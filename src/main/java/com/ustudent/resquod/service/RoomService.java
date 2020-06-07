package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.RoomAlreadyExistsException;
import com.ustudent.resquod.exception.RoomNotFoundException;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final CorporationService corporationService;
    private final RoomValidator roomValidator;

    @Autowired
    RoomService(RoomRepository roomRepository, RoomValidator roomValidator, CorporationService corporationService) {
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
        this.corporationService=corporationService;
    }

    public void addNewRoom(Room newRoom) throws RoomAlreadyExistsException {
        if(!checkIfRoomExists(newRoom)) {
            if (roomValidator.validateRoom(newRoom)) {
                newRoom.setCorporation(corporationService.getCorpoById(newRoom.getCorporation()));
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
}