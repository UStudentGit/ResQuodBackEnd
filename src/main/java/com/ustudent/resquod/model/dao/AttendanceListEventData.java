package com.ustudent.resquod.model.dao;

import java.time.LocalDateTime;

public class AttendanceListEventData {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventRoomData eventData;

    public AttendanceListEventData(Long id,
                                   String name,
                                   LocalDateTime startTime,
                                   LocalDateTime endTime,
                                   EventRoomData eventData) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventData = eventData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long eventId) {
        this.id = id;
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

    public void setEventData(EventRoomData eventData) {
        this.eventData = eventData;
    }

    public EventRoomData getEventData() {
        return eventData;
    }
}