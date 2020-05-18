package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.model.dao.PresenceData;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.service.PresenceService;
import com.ustudent.resquod.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PresenceController {

    private final PresenceService presenceService;
    private final UserService userService;

    @Autowired
    public PresenceController(PresenceService presenceService, UserService userService) {
        this.presenceService = presenceService;
        this.userService = userService;
    }

    @ApiOperation(value = "Returns the current user's presences", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/userPresences")
    public List<PresenceData> getUserPresences() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            UserData userData = userService.getUser(email);
            return presenceService.findUserPresences(userData.getEmail());
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}
