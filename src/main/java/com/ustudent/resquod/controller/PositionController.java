package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.*;
import com.ustudent.resquod.model.dao.CorpoData;
import com.ustudent.resquod.model.dao.EventAndAttendanceListData;
import com.ustudent.resquod.model.dao.NewPositionData;
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
@Api(value = "Position Management")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @ApiOperation(value = "Returns positions with null tag id", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @GetMapping("/nullTagsGetter")
    public List<PositionData> getNullTags(@ApiParam(value = "Required corporation id", required = true)
                                          @RequestBody CorpoData corpoData) {
        return positionService.getNullTags(corpoData);
    }

    @ApiOperation(value = "Setting tag id for possition", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Id Tag updated Succesfully"),
            @ApiResponse(code = 400, message = "\"Invalid Input\" or \"Tag Id Already Exists\""),
            @ApiResponse(code = 404, message = "Position Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @PatchMapping("/TagIdSetter")
    public ResponseTransfer setTagId(@ApiParam(value = "Required position id, tag id", required = true)
                                         @RequestBody PositionData positionData) {
        try {
            positionService.setTagId(positionData);
        } catch (PermissionDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Permission Denied");
        } catch (PositionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Position Does Not Exist");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag Id Already Exists");
        } catch (InvalidInputException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input");
        }
        return new ResponseTransfer("Id Tag Updated Successfully");
    }

    @ApiOperation(value = "Add New Position", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Position Added Succesfully"),
            @ApiResponse(code = 400, message = "\"Invalid Input\" or \"Possition Already Exists\" or \"Id Tag Already Exists\""),
            @ApiResponse(code = 404, message = "Room Does Not Exist"),
            @ApiResponse(code = 401, message = "Permission Denied")})
    @PostMapping("/position")
    public ResponseTransfer addNewPosition(@ApiParam(value = "Required number of position, room id", required = true)
            @RequestBody NewPositionData newPosition) {
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Permission Denied");
        }
        return new ResponseTransfer("Position Added Successfully");
    }

    @ApiOperation(value = "Change position data", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated!"),
            @ApiResponse(code = 400, message = "Invalid input!"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PatchMapping("/positionPatch")
    public ResponseTransfer changePositionData(
            @ApiParam(value = "Required number of position, room id", required = true)
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

    @ApiOperation(value = "Read NFC tag and get presence!", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid input! or There is no event to get Presence!"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @PostMapping("/presenceAtPosition")
    public EventAndAttendanceListData getPresenceAtPosition(
            @ApiParam(value = "Required tagId", required = true)
            @RequestParam String tagId) {
        try {
            return positionService.getPresenceAtPosition(tagId);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!");
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no event to get Presence! or Bad TagId");
        }
    }
}