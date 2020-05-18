package com.ustudent.resquod.model.dao;

public class PositionData {

    private Long Id;
    private Integer numberOfPosition;
    private String tagId;
    private Long room_id;

    public PositionData(Long Id, Integer numberOfPosition, String tagId, Long room_id){
        this.Id = Id;
        this.numberOfPosition = numberOfPosition;
        this.tagId = tagId;
        this.room_id = room_id;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

}