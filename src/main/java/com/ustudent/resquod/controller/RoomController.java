package com.ustudent.resquod.controller;

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
public class RoomController {

    @Autowired
    RoomService roomService;

    @RequestMapping(method= RequestMethod.POST, value = "/newRoom")
    public String addNewRoom(@RequestBody Room newRoom) {
        try {
            roomService.addNewRoom(newRoom);
        } catch (ObjectAlreadyExistsException exception) {
            return "Room already exists";
        } catch (InvalidInputException exception) {
            return "Invalid input.";
        }
        return "Brand added successfully";
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


}
