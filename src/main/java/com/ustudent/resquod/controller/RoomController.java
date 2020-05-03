package com.ustudent.resquod.controller;

import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.exception.ObjectAlreadyExistsException;
import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
