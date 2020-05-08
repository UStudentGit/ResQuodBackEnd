package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Position;
import com.ustudent.resquod.service.PositionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
