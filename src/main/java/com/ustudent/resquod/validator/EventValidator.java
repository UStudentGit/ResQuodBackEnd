package com.ustudent.resquod.validator;

import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventValidator {

    private final EventRepository eventRepository;
    private final RoomValidator roomValidator;

    @Autowired
    public EventValidator(EventRepository eventRepository,RoomValidator roomValidator) {
        this.eventRepository=eventRepository;
        this.roomValidator=roomValidator;
    }

    public boolean checkIfEventExists(Event eventToValidate) {

        if(eventRepository.findByName(eventToValidate.getName()).isPresent())
            return false;
        else
            return true;
    }

    public boolean validateEvent(Event eventToValidate) {

        if(eventToValidate.getName() == null || eventToValidate.getName().isEmpty())
            return false;
        if(!roomValidator.validateRoom(eventToValidate.getRoom()))
            return false;
        if(eventToValidate.getDayOfWeek() == null)
            return false;
        if(eventToValidate.getEventTime() == null)
            return false;
        return true;
    }
}
