package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.EventAlreadyExistsException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.RoomNotFoundException;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.repository.EventRepository;
import com.ustudent.resquod.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventValidator eventValidator;
    private final EventRepository eventRepository;
    private final RoomService roomService;

    @Autowired
    public EventService(EventValidator eventValidator,EventRepository eventRepository,RoomService roomService) {
        this.eventValidator=eventValidator;
        this.eventRepository=eventRepository;
        this.roomService=roomService;
    }

    public void addNewEvent(Event newEvent) throws EventAlreadyExistsException {
        if(!checkIfEventExists(newEvent)) {
            eventValidator.validateEvent(newEvent);
            Room tempRoom = roomService.getRoomById(newEvent.getRoom());
            newEvent.setRoom(tempRoom);
            eventRepository.save(newEvent);
        } else throw new EventAlreadyExistsException();
    }

    private boolean checkIfEventExists(Event newEvent) {
        return eventRepository.findByName(newEvent.getName()).isPresent();
    }
}