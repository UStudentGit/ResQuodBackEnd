package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.repository.EventRepository;
import com.ustudent.resquod.repository.RoomRepository;
import com.ustudent.resquod.validator.EventValidator;
import com.ustudent.resquod.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventValidator eventValidator;
    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;

    @Autowired
    public EventService(EventRepository eventRepository, EventValidator eventValidator,RoomRepository roomRepository,RoomValidator roomValidator) {
        this.eventValidator=eventValidator;
        this.eventRepository=eventRepository;
        this.roomRepository=roomRepository;
        this.roomValidator=roomValidator;
    }

    public void addNewEvent(Event newEvent) throws ObjectAlreadyExistsException, InvalidInputException, ObjectNotFoundException {

        if (eventValidator.checkIfEventExists(newEvent)) {
            if (eventValidator.validateEvent(newEvent)) {
                if (!roomValidator.checkIfRoomExists(newEvent.getRoom())) {
                    newEvent.setRoom(roomRepository.findByName(newEvent.getRoom().getName()).get());
                    eventRepository.save(newEvent);
                } else {
                    throw new ObjectNotFoundException();
                }
            } else
                throw new InvalidInputException();
        } else
            throw new ObjectAlreadyExistsException();
    }
}
