package com.ustudent.resquod.controller;


import com.ustudent.resquod.model.dao.NewEventData;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.dao.EventDTO;
import com.ustudent.resquod.model.dao.EventData;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.EventService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Set;



@RestController
@Api(value="Event Management")
public class EventController {

    private final EventService eventService;


    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ApiOperation(value = "Returns Admin events List", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/adminEvents")
    public Set<EventDTO> getAdminEvents() {
        try {
            return eventService.findEventsWhereUserIsAdmin();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @ApiOperation(value = "Returns user events List", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/userEvents")
    public Set<EventDTO> getUserEvents() {
        try {
            return eventService.findUserEvents();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @ApiOperation(value = "Change Event data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Invalid Password!\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PatchMapping("/eventChanges")
    public ResponseTransfer changeEventData(
            @ApiParam(value = "Required id, name, Event Room id, AdminPassword", required = true)
            @RequestBody EventData userInput) {
        try {
            eventService.changeEventData(userInput);
        }catch (InvalidAdminId ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Permisson!");
        }
        catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        }catch (PasswordAlreadyExists ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Try another Password/Key to event!");
        }catch (RoomDoesntBelongToYourCorpo ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Cannot use this room");
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object not Found");
        }
        return new ResponseTransfer("Successfully updated!");
    }


    @ApiOperation(value = "Add New Event", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Event Added Succesfully"),
            @ApiResponse(code = 400, message = "\"Invalid Input\" or \"Event Already Exists\" or \"Permission Denied\""),
            @ApiResponse( code = 404, message = "Room Does Not Exist")})
    @PostMapping("/event")
    public ResponseTransfer addNewEvent(@ApiParam(value = "Required name, password, room id", required = true)
                                            @RequestBody NewEventData newEvent) {
        try {
            eventService.addNewEvent(newEvent);
        } catch (EventAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event Already Exists");
        } catch (RoomNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Does Not Exist");
        } catch (InvalidInputException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input");
        } catch (PermissionDeniedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission Denied");
        }
        return new ResponseTransfer("Event Added Successfully");
    }
}