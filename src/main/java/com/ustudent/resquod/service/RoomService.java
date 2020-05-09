package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomValidator roomValidator) {
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
    }

    public void addNewRoom(Room newRoom) throws ObjectAlreadyExistsException, InvalidInputException {

        if(roomValidator.checkIfRoomExists(newRoom)) {
            if (roomValidator.validateRoom(newRoom))
                roomRepository.save(newRoom);
            else
                throw new InvalidInputException();
        }
        else
            throw new ObjectAlreadyExistsException();
    }
}
