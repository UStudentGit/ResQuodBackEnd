package com.ustudent.resquod.model.dao;

import java.time.LocalDateTime;

public class AttendanceListData {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private Long eventId;
    private String stringRequest;

    public AttendanceListData(Long id, String name, LocalDateTime createTime, Long eventId){
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.eventId = eventId;
    }
    public AttendanceListData(){}

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

    public Long getEventID() {
        return eventId;
    }

    public void setEventID(Long event_id) {
        this.eventId = event_id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getStringRequest() {
        return stringRequest;
    }

    public void setStringRequest(String stringRequest) {
        this.stringRequest = stringRequest;
    }
}