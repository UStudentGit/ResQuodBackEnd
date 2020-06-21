package com.ustudent.resquod.model.dao;

public class EventRoomData {
    private Long id;
    private String name;
    private String password;
    private NewRoomData roomData;

    public EventRoomData(Long id, String name, String password, NewRoomData roomData) {
        this.id = id;
        this.name = name;
        this.roomData = roomData;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public NewRoomData getRoomData() { return roomData; }
    public void setRoomData(NewRoomData roomData) { this.roomData = roomData; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}