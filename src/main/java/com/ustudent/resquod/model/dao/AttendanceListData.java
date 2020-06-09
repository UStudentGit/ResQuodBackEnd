package com.ustudent.resquod.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class AttendanceListData {
    @JsonIgnore
    private Long id;
    private String name;
    private Long eventId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public AttendanceListData(Long id, String name, LocalDateTime startTime, LocalDateTime endTime, Long eventId){
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = eventId;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}