package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.EventAlreadyExistsException;
import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.repository.EventRepository;
import com.ustudent.resquod.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventValidator eventValidator;
    private final EventRepository eventRepository;
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public EventService(EventValidator eventValidator,
                        EventRepository eventRepository,
                        RoomService roomService,
                        UserService userService) {
        this.eventValidator=eventValidator;
        this.eventRepository=eventRepository;
        this.roomService=roomService;
        this.userService=userService;
    }

    public void addNewEvent(Event newEvent) throws EventAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
        Corporation corporation = roomService.getRoomById(newEvent.getRoom()).getCorporation();

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if (!checkIfEventExists(newEvent)) {
            eventValidator.validateEvent(newEvent);
            newEvent.setAdministratorId(admin.getId());
            Room tempRoom = roomService.getRoomById(newEvent.getRoom());
            newEvent.setRoom(tempRoom);
            eventRepository.save(newEvent);
        } else throw new EventAlreadyExistsException();
    }

    private boolean checkIfEventExists(Event newEvent) {
        return eventRepository.findByName(newEvent.getName(),newEvent.getRoom().getId()).isPresent();
    }
}