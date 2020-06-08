package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.EventAlreadyExistsException;
import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.NewEventData;
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

    public void addNewEvent(NewEventData newEvent) throws EventAlreadyExistsException, PermissionDeniedException {

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User admin = userService.getUserByEmail(email);
        Corporation corporation = roomService.getRoomById(newEvent.getRoomId()).getCorporation();

        if (!(admin.getRole().equals("ROLE_ADMIN") ||
                (admin.getRole().equals("ROLE_OWNER") && admin.getCorporations().contains(corporation))))
            throw new PermissionDeniedException();

        if (!checkIfEventExists(newEvent)) {
            eventValidator.validateEvent(newEvent);
            Event event = new Event();
            event.setAdministratorId(admin.getId());
            Room room = roomService.getRoomById(newEvent.getRoomId());
            event.setName(newEvent.getName());
            event.setPassword(newEvent.getPassword());
            event.setRoom(room);
            eventRepository.save(event);
        } else throw new EventAlreadyExistsException();
    }

    private boolean checkIfEventExists(NewEventData newEvent) {
        return eventRepository.findByName(newEvent.getName(),newEvent.getRoomId()).isPresent();
    }
}