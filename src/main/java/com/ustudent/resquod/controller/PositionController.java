package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.dao.PositionData;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.PositionService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PositionController {

    final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/newPosition")
    public String addNewRoom(@RequestBody Position newPosition) {
        try {
            positionService.addNewPosition(newPosition);
        } catch (ObjectAlreadyExistsException exception) {
            return "Room already exists.";
        } catch (InvalidInputException exception) {
            return "Invalid input.";
        } catch (ObjectNotFoundException exception) {
            return "Room does not exists.";
        }
        return "Position added successfully.";
    }

    @ApiOperation(value = "Change position data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "Invalid input!"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PatchMapping("/positionPatch")
    public ResponseTransfer changePositionData(
            @ApiParam(value = "Required id, numberOfPosition, tagId, room_id", required = true)
            @RequestBody PositionData positionInput) {
        try {
            positionService.updatePosition(positionInput);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        } catch (RoomNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room doesn't exist");
        } catch (PositionNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Position doesn't exist");
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return new ResponseTransfer("Successfully updated!");
    }
}
