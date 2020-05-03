package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.repository.PositionRepository;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.PositionValidator;
import com.ustudent.resquod.validator.RoomValidator;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private PositionRepository positionRepository;
    private PositionValidator positionValidator;
    private RoomRepository roomRepository;
    private RoomValidator roomValidator;

    @Autowired
    PositionService(PositionRepository positionRepository,PositionValidator positionValidator,
                    RoomRepository roomRepository,RoomValidator roomValidator) {
        this.positionRepository=positionRepository;
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
        this.positionValidator=positionValidator;
    }

    public void addNewPosition(Position newPosition) throws ObjectAlreadyExistsException, InvalidInputException, ObjectNotFoundException {

        if(positionValidator.checkIfPositionExists(newPosition)) {
            if(positionValidator.validatePosition(newPosition)) {
                if (!roomValidator.checkIfRoomExists(newPosition.getRoom())) {
                    newPosition.setRoom(roomRepository.findByName(newPosition.getRoom().getName()).get());
                    positionRepository.save(newPosition);
                } else {
                    throw new ObjectNotFoundException();
                }
            }
            else
                throw new InvalidInputException();
        }
        else
            throw new ObjectAlreadyExistsException();
    }
}
