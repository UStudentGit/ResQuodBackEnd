package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.PositionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Api(value="Position Management")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService=positionService;
    }

    @ApiOperation(value = "Add New Position")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Position Added Succesfully."),
            @ApiResponse(code = 400, message = "\"Invalid Input.\" or \"Room Already Exists.\""),
            @ApiResponse( code = 404, message = "Room Does Not Exist.")})
    @RequestMapping(method= RequestMethod.POST, value = "/newPosition")
    public ResponseTransfer addNewPosition(@ApiParam(value = "Required Number Of Position, Room") @RequestBody Position newPosition) throws ResponseStatusException {
        try {
            positionService.addNewPosition(newPosition);
        } catch (ObjectAlreadyExistsException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room Already Exists.", exception);
        } catch (InvalidInputException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input.", exception);
        } catch (ObjectNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Does Not Exist.", exception);
        }
        return new ResponseTransfer("Position Added Successfully.");
    }
}
