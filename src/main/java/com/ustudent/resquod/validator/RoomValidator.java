package com.ustudent.resquod.validator;

import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    @Autowired
    RoomRepository roomRepository;

    public boolean checkIfRoomExists(Room roomToValidate) {

        if(roomRepository.findByName(roomToValidate.getName()).isPresent())
            return false;
        else
            return true;
    }

    public boolean validateRoom(Room roomToValidate) {

        if(roomToValidate.getName() != null && (!roomToValidate.getName().isEmpty()))
            return true;
        else
            return false;
    }
}
