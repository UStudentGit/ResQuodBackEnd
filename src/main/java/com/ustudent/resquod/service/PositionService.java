package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.*;
import com.ustudent.resquod.model.dao.*;
import com.ustudent.resquod.repository.PositionRepository;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.repository.UserRepository;
import com.ustudent.resquod.validator.PositionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionValidator positionValidator;
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PresenceService presenceService;

    @Autowired
    PositionService(PositionRepository positionRepository, PositionValidator positionValidator,
                    RoomRepository roomRepository, UserRepository userRepository, RoomService roomService,
                    UserService userService, PresenceService presenceService) {
        this.positionRepository = positionRepository;
        this.roomRepository = roomRepository;
        this.positionValidator = positionValidator;
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.presenceService = presenceService;
    }

    public void addNewPosition(NewPositionData newPosition) throws PositionAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
        Corporation corporation = roomService.getRoomById(newPosition.getRoomId()).getCorporation();

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if (!checkIfPositionExists(newPosition)) {
            if (positionValidator.validatePosition(newPosition)) {
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
        return positionRepository.findByNumberOfPosition(positionToValidate.getNumberOfPosition(), positionToValidate.getRoomId()).isPresent();
    }

    public void updatePosition(PositionData positionInput) throws EmailExistException, PositionNotFoundException, RoomNotFoundException, InvalidInputException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserData userData = userRepository.findUserData(email).orElseThrow(EmailExistException::new);
        Position position = positionRepository.findByIdAndEmail(positionInput.getId(), userData.getEmail()).orElseThrow(PositionNotFoundException::new);
        if (positionInput.getTagId() == null || positionInput.getTagId().length() < 1 || positionInput.getNumberOfPosition() == null)
            throw new InvalidInputException();
        position.setTagId(positionInput.getTagId());
        position.setNumberOfPosition(positionInput.getNumberOfPosition());
        Room room = roomRepository.findByRoomIdAndOwnerEmail(positionInput.getRoomId(), email).orElseThrow(RoomNotFoundException::new);
        position.setRoom(room);
        positionRepository.save(position);
    }

    public EventAndAttendanceListData getPresenceAtPosition(String tagId) {
        if (tagId == null || tagId.isEmpty()) throw new InvalidInputException();
        LocalDateTime date = LocalDateTime.now();
        User user = userService.getLoggedUser();
        Presence presence=presenceService.getPresence(tagId, date, user.getId());
        EventAndAttendanceListData eventAndAttendanceListData=new EventAndAttendanceListData();
        eventAndAttendanceListData.setPresenceAt(presence.getDate());
        eventAndAttendanceListData.setAttendanceListId(presence.getAttendanceList().getId());
        eventAndAttendanceListData.setAttendanceListName(presence.getAttendanceList().getName());
        eventAndAttendanceListData.setStartTime(presence.getAttendanceList().getStartTime());
        eventAndAttendanceListData.setEndTime(presence.getAttendanceList().getEndTime());
        eventAndAttendanceListData.setEventId(presence.getAttendanceList().getEvent().getId());
        eventAndAttendanceListData.setEventName(presence.getAttendanceList().getEvent().getName());
        return eventAndAttendanceListData;
    }
}