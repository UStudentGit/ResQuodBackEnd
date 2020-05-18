package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.model.dao.AttendanceListData;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.service.AttendanceListService;
import com.ustudent.resquod.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AttendanceListController {

    private final AttendanceListService attendanceListService;
    private final UserService userService;

    @Autowired
    public AttendanceListController(AttendanceListService attendanceListService, UserService userService) {
        this.attendanceListService = attendanceListService;
        this.userService = userService;
    }

    @ApiOperation(value = "Returns the current user's attendance lists", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/userAttendanceLists")
    public List<AttendanceListData> getUserAttendanceLists() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            UserData userData = userService.getUser(email);
            return attendanceListService.findUserAttendanceLists(userData.getEmail());
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}