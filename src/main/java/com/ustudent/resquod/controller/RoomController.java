package com.ustudent.resquod.controller;


import com.ustudent.resquod.model.dao.NewRoomData;
import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.model.dao.RoomDTO;
import com.ustudent.resquod.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Set;


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
    public ResponseTransfer addNewRoom(@ApiParam(value = "Required name, corporation id", required = true)
                                           @RequestBody NewRoomData newRoom) {
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

    @ApiOperation(value = "Returns corporation rooms list", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/corpoRooms")
    public @ResponseBody Set<RoomDTO> getCorpoRooms(@RequestParam Long id)
    {
        try {
            return roomService.findCorpoRooms(id);
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }

    @ApiOperation(value = "Change Room data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"No Permission\""),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PatchMapping("/roomEditor")
    public ResponseTransfer changeRoomData(
            @ApiParam(value = "Required Room id, Room name", required = true)
            @RequestBody RoomDTO userInput) {
        try {
                roomService.editRoomData(userInput);
        }catch (InvalidAdminId ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Permisson!");
        }
        catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseTransfer("Successfully updated!");
    }

    @ApiOperation(value = "Remove Room", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Room Removed Succesfully"),
            @ApiResponse(code = 400, message = "Permission Denied"),
            @ApiResponse( code = 404, message = "Room Does Not Exist")})
    @PostMapping("/roomRemoval")
    public ResponseTransfer deleteRoom(@ApiParam(value = "Required room id", required = true)
                                       @RequestBody NewRoomData newRoom) {
        try {
            roomService.removeRoom(newRoom);
        } catch (PermissionDeniedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission Denied");
        } catch (RoomNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Does Not Exist");
        }
        return new ResponseTransfer("Room Removed Successfully");
    }
}