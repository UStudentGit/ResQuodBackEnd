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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionValidator positionValidator;
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;

    @Autowired
    PositionService(PositionRepository positionRepository,PositionValidator positionValidator,
                    RoomRepository roomRepository, UserRepository userRepository,RoomService roomService) {
        this.positionRepository=positionRepository;
        this.roomRepository=roomRepository;
        this.positionValidator=positionValidator;
        this.userRepository = userRepository;
        this.roomService=roomService;
    }

    public void addNewPosition(Position newPosition) throws PositionAlreadyExistsException {

        if(!checkIfPositionExists(newPosition)) {
            if(positionValidator.validatePosition(newPosition)) {
                Room tempRoom = roomService.getRoomById(newPosition.getRoom());
                if (tempRoom != null) {
                    newPosition.setRoom(tempRoom);
                    positionRepository.save(newPosition);
                } else throw new RoomNotFoundException();
            }
        } else throw new PositionAlreadyExistsException();
    }

    private boolean checkIfPositionExists(Position positionToValidate) {
        return positionRepository.findByNumberOfPosition(positionToValidate.getNumberOfPosition(),positionToValidate.getRoom().getId()).isPresent();
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