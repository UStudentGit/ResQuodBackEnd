package com.ustudent.resquod.controller;


import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.dao.CorpoData;
import com.ustudent.resquod.model.dao.ResponseTransfer;
import com.ustudent.resquod.service.CorporationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Api(value="Corporation Management")
public class CorporationController {

    private final CorporationService corporationService;

    @Autowired
    public CorporationController(CorporationService corporationService) {
        this.corporationService = corporationService;
    }


    @ApiOperation(value = "Create new Corpo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created!"),
            @ApiResponse(code = 400, message = "\"Invalid input!\" or \"Name already taken!\""),
            @ApiResponse(code = 403, message = "You Have no permission"),
            @ApiResponse(code = 500, message = "Corporation cannot be registered!")})
    @PostMapping(value = "/corpoRegister")
    public ResponseTransfer register(
            @ApiParam(value = "Required name", required = true)
            @RequestBody CorpoData inputData) {
        try {
            corporationService.addNewCorpo(inputData);
        } catch (InvalidInputException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input!", ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Corporation cannot be created!");
        }
        return new ResponseTransfer("Successfully created!");

    }

    @ApiOperation(value = "Returns all Corporations", authorizations = {@Authorization(value = "authkey")})
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 403, message = "You Have no permission"),
            @ApiResponse(code = 500, message = "Server Error!")})
    @GetMapping(value = "/allCorpos")
    public List<CorpoData> showAllCorpos() {
        try {
            return corporationService.showEveryCorpo();
        } catch (EmailExistException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
    }
}
