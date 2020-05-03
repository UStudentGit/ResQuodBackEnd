package com.ustudent.resquod.validator;

import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionValidator {

    private PositionRepository positionRepository;
    private RoomValidator roomValidator;

    @Autowired
    PositionValidator(PositionRepository positionRepository,RoomValidator roomValidator) {

        this.positionRepository=positionRepository;
        this.roomValidator=roomValidator;
    }

    public boolean checkIfPositionExists(Position positionToValidate) {

        if(positionRepository.findByNumberOfPosition(positionToValidate.getNumberOfPosition()).isPresent())
            return false;
        else
            return true;
    }

    public boolean validatePosition(Position positionToValidate) {

        if(positionToValidate.getNumberOfPosition() == null)
            return false;
        if(!roomValidator.validateRoom(positionToValidate.getRoom()))
            return false;
        return true;
    }
}
