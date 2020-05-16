package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.dao.PositionData;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.repository.PositionRepository;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.repository.UserRepository;
import com.ustudent.resquod.validator.PositionValidator;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private PositionRepository positionRepository;
    private PositionValidator positionValidator;
    private RoomRepository roomRepository;
    private RoomValidator roomValidator;
    private UserRepository userRepository;

    @Autowired
    PositionService(PositionRepository positionRepository,PositionValidator positionValidator,
                    RoomRepository roomRepository,RoomValidator roomValidator, UserRepository userRepository) {
        this.positionRepository=positionRepository;
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
        this.positionValidator=positionValidator;
        this.userRepository = userRepository;
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

    public void updatePosition(PositionData positionInput) throws EmailExistException, PositionNotFoundException, RoomNotFoundException, InvalidInputException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserData userData = userRepository.findUserData(email).orElseThrow(EmailExistException::new);
        Position position = positionRepository.findByIdAndEmail(positionInput.getId(), userData.getEmail()).orElseThrow(PositionNotFoundException::new);
        if(positionInput.getTagId() == null || positionInput.getTagId().length() < 1 || positionInput.getNumberOfPosition() == null)
            throw new InvalidInputException();
        position.setTagId(positionInput.getTagId());
        position.setNumberOfPosition(positionInput.getNumberOfPosition());
        Room room = roomRepository.findByRoomIdAndOwnerEmail(positionInput.getRoomId(), email).orElseThrow(RoomNotFoundException::new);
        position.setRoom(room);
        positionRepository.save(position);
    }
}
