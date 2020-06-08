package com.ustudent.resquod.validator;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomValidator {

    public boolean validateRoom(Room roomToValidate) throws InvalidInputException {
        if(roomToValidate.getName() == null || roomToValidate.getName().isEmpty())
            throw new InvalidInputException();
        return true;
    }
}
