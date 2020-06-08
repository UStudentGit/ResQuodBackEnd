package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.NewPositionData;
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
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    PositionService(PositionRepository positionRepository,PositionValidator positionValidator,
                    RoomRepository roomRepository, UserRepository userRepository,RoomService roomService,
                    UserService userService) {
        this.positionRepository=positionRepository;
        this.roomRepository=roomRepository;
        this.positionValidator=positionValidator;
        this.userRepository = userRepository;
        this.roomService=roomService;
        this.userService=userService;
    }

    public void addNewPosition(NewPositionData newPosition) throws PositionAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
        Corporation corporation = roomService.getRoomById(newPosition.getRoomId()).getCorporation();

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if(!checkIfPositionExists(newPosition)) {
            if(positionValidator.validatePosition(newPosition)) {
                Room room = roomService.getRoomById(newPosition.getRoomId());
                Position position = new Position();
                position.setNumberOfPosition(newPosition.getNumberOfPosition());
                position.setRoom(room);
                position.setTagId(null);
                positionRepository.save(position);
            }
        } else throw new PositionAlreadyExistsException();
    }

    private boolean checkIfPositionExists(NewPositionData positionToValidate) {
        return positionRepository.findByNumberOfPosition(positionToValidate.getNumberOfPosition(),positionToValidate.getRoomId()).isPresent();
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