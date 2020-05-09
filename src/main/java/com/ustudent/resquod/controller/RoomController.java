package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value = "Room Management")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService=roomService;
    }

    @ApiOperation(value = "Add New Room")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Room Added Succesfully."),
            @ApiResponse(code = 400, message = "\"Invalid Input.\" or \"Room Already Exists.\"")})
    @RequestMapping(method= RequestMethod.POST, value = "/newRoom")
    public ResponseTransfer addNewRoom(@ApiParam(value = "Required Name Of Room") @RequestBody Room newRoom) {
        try {
            roomService.addNewRoom(newRoom);
        } catch (ObjectAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Already Exists.", exception);
        } catch (InvalidInputException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input.", exception);
        }
        return new ResponseTransfer("Room added successfully.");
    }
}
