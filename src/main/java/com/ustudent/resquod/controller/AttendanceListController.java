package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.AttendanceList;
import com.ustudent.resquod.model.dao.AttendanceListData;
import com.ustudent.resquod.model.dao.AttendanceListEventData;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.service.AttendanceListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@RestController
public class AttendanceListController {

    private final AttendanceListService attendanceListService;

    @Autowired
    public AttendanceListController(AttendanceListService attendanceListService) {
        this.attendanceListService = attendanceListService;
    }

    @ApiOperation(value = "Creates attendance list for specified event", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Attendance List Created!"),
            @ApiResponse(code = 400, message = "Error while creating list or Event not found."),
            @ApiResponse(code = 500, message = "Server Error!")
    })
    @PostMapping("/attendancelist")
    public ResponseTransfer createAttendanceList(@RequestBody HashSet<AttendanceListData> attendanceRequest){
        try {
            for (AttendanceListData attList:attendanceRequest) {
                attendanceListService.createAttendanceList(attList);
            }
            return new ResponseTransfer("AttendanceList created!");

        }
        catch(PermissionDeniedException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while creating list. Permission denied.");
        }
        catch(EventNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while creating list. Event not found.");
        }
        catch(UserNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while creating list. User not found.");
        }
    }

    @ApiOperation(value = "Returns attendance list for specified event", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "\"Event not found!\" or \"Attendance lists for this event not found!\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping("/attendancelist/{eventId}")
    public List<AttendanceListEventData> getAttendanceList(@PathVariable(value="eventId") Long eventId) {
        try {
            return attendanceListService.getAttendanceListEvent(eventId);
        } catch (EventNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not found!");
        } catch (ObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance lists for this event not found!");
        }
    }

    @ApiOperation(value = "Returns present users data from specified Attendance List", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping("/presence/{attendanceListId}")
    public List<UserData> getPresentUsers(@PathVariable(value="attendanceListId") Long attendanceListId){
        try {
            return attendanceListService.getPresentUsers(attendanceListId);
        } catch (AttendanceListNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance list not found! Bad request");
        }
    }

    @ApiOperation(value = "Returns absent users data from specified Attendance List", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping("/absence/{attendanceListId}")
    public List<UserData> getAbsentUsers(@PathVariable(value="attendanceListId") Long attendanceListId){
        try {
            return attendanceListService.getAbsentUsers(attendanceListId);
        } catch (AttendanceListNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance list not found! Bad request");
        }
    }


    @ApiOperation(value = "Returns the current user's attendance lists", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/userAttendanceLists")
    public List<AttendanceListData> getUserAttendanceLists() {
        try {
            return attendanceListService.findUserAttendanceLists();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}