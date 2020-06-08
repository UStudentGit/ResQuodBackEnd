package com.ustudent.resquod.validator;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.dao.NewEventData;
import org.springframework.stereotype.Component;

@Component
public class EventValidator {

    public boolean validateEvent(NewEventData eventToValidate) throws InvalidInputException {

        if(eventToValidate.getName() == null || eventToValidate.getName().isEmpty()
        || eventToValidate.getRoomId() == null)
            throw new InvalidInputException();
        return true;
    }
}
