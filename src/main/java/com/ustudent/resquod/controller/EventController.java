package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.EventService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value="Event Management")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService=eventService;
    }

    @ApiOperation(value = "Add New Event")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Event Added Succesfully."),
            @ApiResponse(code = 400, message = "\"Invalid Input.\" or \"Event Already Exists.\""),
            @ApiResponse( code = 404, message = "Room Does Not Exists.")})
    @RequestMapping(method= RequestMethod.POST, value = "/newEvent")
    public ResponseTransfer addNewEvent(@ApiParam(value = "Required Event Name, Date, Time, Room", required = true) @RequestBody Event newEvent) {

        try {
            eventService.addNewEvent(newEvent);
        } catch (InvalidInputException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input.", exception);
        } catch (ObjectAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event Already Exists.", exception);
        } catch (ObjectNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Does Not Exists.");
        }
        return new ResponseTransfer("Event Added Successfully.");
    }
}