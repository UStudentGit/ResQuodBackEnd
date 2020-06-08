package com.ustudent.resquod.validator;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventValidator {

    public boolean validateEvent(Event eventToValidate) throws InvalidInputException {

        if(eventToValidate.getName() == null || eventToValidate.getName().isEmpty()
        || eventToValidate.getRoom().getId() == null)
            throw new InvalidInputException();
        return true;
    }
}
