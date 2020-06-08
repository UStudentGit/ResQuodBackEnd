package com.ustudent.resquod.validator;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.dao.NewPositionData;
import org.springframework.stereotype.Component;

@Component
public class PositionValidator {

    public boolean validatePosition(NewPositionData positionToValidate) throws InvalidInputException {

        if(positionToValidate.getNumberOfPosition() == null)
            throw new InvalidInputException();
        return true;
    }
}