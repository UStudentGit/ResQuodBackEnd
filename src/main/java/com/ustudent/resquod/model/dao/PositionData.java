package com.ustudent.resquod.model.dao;

public class PositionData {

    private Long id;
    private Integer numberOfPosition;
    private String tagId;
    private Long room_id;
    private String roomName;

    public PositionData(Long id, Integer numberOfPosition, String tagId, Long room_id, String roomName){
        this.id = id;
        this.numberOfPosition = numberOfPosition;
        this.tagId = tagId;
        this.room_id = room_id;
        this.roomName= roomName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public Integer getNumberOfPosition() {
        return numberOfPosition;
    }

    public void setNumberOfPosition(Integer numberOfPosition) {
        this.numberOfPosition = numberOfPosition;
    }

    public Long getRoomId() {
        return room_id;
    }

    public void setRoomId(Long room_id) {
        this.room_id = room_id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}