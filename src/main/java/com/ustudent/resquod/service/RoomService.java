package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.exception.RoomAlreadyExistsException;
import com.ustudent.resquod.exception.RoomNotFoundException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.NewRoomData;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public void addNewRoom(NewRoomData newRoom) throws RoomAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
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
}