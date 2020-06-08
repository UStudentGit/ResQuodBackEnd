package com.ustudent.resquod.model.dao;

public class EventData {
    private Long id;
    private String name;
    private Long roomId;
    private String password;

    public EventData(Long id, String name, Long roomId, String password) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
