package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.validator.EventValidator;
import com.ustudent.resquod.model.dao.EventDTO;
import com.ustudent.resquod.model.dao.EventData;
import com.ustudent.resquod.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

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
    
      public Set<EventDTO> findEventsWhereUserIsAdmin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getUserByEmail(email);
        Set<EventDTO> events = eventRepository.findByAdministratorId(user.getId());
        return events ;
    }

    public Set<EventDTO> findUserEvents(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getUserByEmail(email);
        Set<Event> events = user.getEvents();
        Set<EventDTO> eventsU = new HashSet<>();
        for (Event e: events) {
            eventsU.add(new EventDTO(e.getId(),e.getName(),e.getAdministratorId(),e.getPassword(),e.getRoom().getId(),e.getRoom().getName()));
        }
    return eventsU;
    }

    public void changeEventData(EventData inputData) throws InvalidInputException, ObjectNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getUserByEmail(email);
        Event event = eventRepository.findById(inputData.getId()).orElseThrow(ObjectNotFoundException::new);
        Room room = roomService.findById(inputData.getRoomId());
        Corporation corpoId = event.getRoom().getCorporation();
        if(!(user.getId().equals(event.getAdministratorId()) || (user.getRole().equals("ROLE_OWNER") && user.getCorporations().contains(corpoId)) || user.getRole().equals("ROLE_ADMIN")))
            throw new InvalidAdminId();
        if(inputData.getName() == null || inputData.getName().length() <2
                || inputData.getRoomId() ==null)
         throw new InvalidInputException();
        event.setName(inputData.getName());
        if(eventRepository.findByPassword(inputData.getPassword()).isPresent())
            throw new PasswordAlreadyExists();
        event.setPassword(inputData.getPassword());
        if(!(event.getRoom().getCorporation().getId().equals(room.getCorporation().getId())))
            throw new RoomDoesntBelongToYourCorpo();
        event.setRoom(room);
        eventRepository.save(event);

    }
}
