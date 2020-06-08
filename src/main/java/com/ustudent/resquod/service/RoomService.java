package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.exception.RoomAlreadyExistsException;
import com.ustudent.resquod.exception.RoomNotFoundException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.exception.InvalidAdminId;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.dao.RoomDTO;

import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final CorporationService corporationService;
    private final UserService userService;
    private final RoomValidator roomValidator;

    @Autowired
    RoomService(RoomRepository roomRepository,
                RoomValidator roomValidator,
                CorporationService corporationService,
                UserService userService) {
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
        this.corporationService=corporationService;
        this.userService=userService;

    }

    public void addNewRoom(Room newRoom) throws RoomAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
        Corporation corporation = corporationService.getCorpoById(newRoom.getCorporation());

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if(!checkIfRoomExists(newRoom)) {
            if(roomValidator.validateRoom(newRoom)) {
                newRoom.setCorporation(corporation);
                roomRepository.save(newRoom);
            }
        } else throw new RoomAlreadyExistsException();
    }

    public boolean checkIfRoomExists(Room roomToValidate) {
        return roomRepository.findByName(roomToValidate.getName()).isPresent();
    }

    public Room getRoomById(Room roomToValidate) {
        return roomRepository.findById(roomToValidate.getId()).orElseThrow(RoomNotFoundException::new);
    }



    public Room findById(Long id) throws ObjectNotFoundException{
        return roomRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public Set<RoomDTO> findCorpoRooms(Long id){
        Set<RoomDTO> cRooms = roomRepository.findByCorporationId(id);
        return cRooms;
    }

    public void editRoomData(RoomDTO inputData) throws InvalidInputException, ObjectNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getUserByEmail(email);
        Room room=  roomRepository.findById(inputData.getId()).orElseThrow(ObjectNotFoundException::new);
        Corporation corpo = room.getCorporation();
        if(user.getRole().equals("ROLE_ADMIN") || (user.getRole().equals("ROLE_OWNER")  && user.getCorporations().contains(corpo)))
            throw new InvalidAdminId();
        if(inputData.getName() == null || inputData.getName().length() <2)
            throw new InvalidInputException();
        room.setName(inputData.getName());
        roomRepository.save(room);
    }

}

