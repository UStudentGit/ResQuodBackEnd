package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.CorporationNotFoundException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.exception.RoomAlreadyExistsException;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value="Room Management")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService=roomService;
    }

    @ApiOperation(value = "Add New Room", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Room Added Succesfully"),
            @ApiResponse(code = 400, message = "\"Invalid Input\" or \"Room Already Exists\" or \"Permission Denied"),
            @ApiResponse( code = 404, message = "Corporation Does Not Exist")})
    @PostMapping("/room")
    public ResponseTransfer addNewRoom(@ApiParam(value = "Required name, corporation", required = true)
                                           @RequestBody Room newRoom) {
        try {
            roomService.addNewRoom(newRoom);
        } catch (RoomAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Already Exists");
        } catch (InvalidInputException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input");
        } catch (CorporationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corporation Does Not Exist");
        } catch (PermissionDeniedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission Denied");
        }
        return new ResponseTransfer("Room Added Successfully");
    }
}