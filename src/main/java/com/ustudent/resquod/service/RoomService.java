package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.exception.RoomAlreadyExistsException;
import com.ustudent.resquod.exception.RoomNotFoundException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.NewRoomData;
import com.ustudent.resquod.exception.InvalidAdminId;
import com.ustudent.resquod.exception.InvalidInputException;
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
        this.roomRepository = roomRepository;
        this.roomValidator = roomValidator;
        this.corporationService = corporationService;
        this.userService = userService;
    }

    public void addNewRoom(NewRoomData newRoom) throws RoomAlreadyExistsException, PermissionDeniedException {

        User admin = userService.getLoggedUser();
        Corporation corporation = corporationService.getCorpoById(newRoom.getCorporationId());

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if(!checkIfRoomExists(newRoom)) {
            if(roomValidator.validateRoom(newRoom)) {
                Room room = new Room();
                room.setName(newRoom.getName());
                room.setCorporation(corporation);
                roomRepository.save(room);
            }
        } else throw new RoomAlreadyExistsException();
    }

    public boolean checkIfRoomExists(NewRoomData roomToValidate) {
        return roomRepository.findByName(roomToValidate.getName()).isPresent();
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }

    public void removeRoom(NewRoomData roomToRemove) {

        if(roomToRemove.getId() == null)
            throw new InvalidInputException();

        User admin = userService.getLoggedUser();
        Room room = roomRepository.findById(roomToRemove.getId()).orElseThrow(RoomNotFoundException::new);
        Corporation corporation = corporationService.getCorpoById(room.getCorporation().getId());

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        roomRepository.delete(room);
    }

    public Room findById(Long id) throws ObjectNotFoundException {
        return roomRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public Set<RoomDTO> findCorpoRooms(Long id) {
        Set<RoomDTO> cRooms = roomRepository.findByCorporationId(id);
        return cRooms;
    }

    public void editRoomData(RoomDTO inputData) throws InvalidInputException, ObjectNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getUserByEmail(email);
        Room room = roomRepository.findById(inputData.getId()).orElseThrow(ObjectNotFoundException::new);
        Corporation corpo = room.getCorporation();
        if (user.getRole().equals("ROLE_ADMIN") || (user.getRole().equals("ROLE_OWNER") && user.getCorporations().contains(corpo)))
            throw new InvalidAdminId();
        if (inputData.getName() == null || inputData.getName().length() < 2)
            throw new InvalidInputException();
        room.setName(inputData.getName());
        roomRepository.save(room);
    }

}

