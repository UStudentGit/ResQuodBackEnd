package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.dao.PositionData;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.PositionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value="Position Management")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @ApiOperation(value = "Add New Position", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Position Added Succesfully"),
            @ApiResponse(code = 400, message = "\"Invalid Input\" or \"Possition Already Exists\" or \"Id Tag Already Exists\" or \"Permission Denied\""),
            @ApiResponse( code = 404, message = "Room Does Not Exist")})
    @PostMapping("/position")
    public ResponseTransfer addNewPosition(@ApiParam(value = "Required number of position, room id", required = true,
            examples = @Example(value = {@ExampleProperty(value = "{'numberOfPosition': Integer, 'room': {'id': Long}}", mediaType = "application/json")}))
                                               @RequestBody Position newPosition) {
        try {
            positionService.addNewPosition(newPosition);
        } catch (PositionAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Position Already Exists");
        } catch (InvalidInputException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input");
        } catch (RoomNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Does Not Exist");
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id Tag Already Exists");
        } catch (PermissionDeniedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission Denied");
        }
        return new ResponseTransfer("Position Added Successfully");
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
