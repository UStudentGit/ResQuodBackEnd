package com.ustudent.resquod.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventDTO {
    private Long id;
    private String name;
    private Long administratorId;
    private String password;
    @JsonProperty
    private RoomDTO room;


    public EventDTO(Long id, String name, Long administratorId, String password, RoomDTO room) {
        this.id = id;
        this.name = name;
        this.administratorId = administratorId;
        this.password = password;
        this.room=room;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
