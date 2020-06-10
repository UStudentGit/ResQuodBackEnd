package com.ustudent.resquod.model.dao;

import java.time.LocalDateTime;

public class EventAndAttendanceListData {
    private Long eventId;
    private String eventName;
    private Long attendanceListId;
    private String attendanceListName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime presenceAt;

    public EventAndAttendanceListData() {
    }

    public String getEventName() {
        return eventName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getAttendanceListId() {
        return attendanceListId;
    }

    public void setAttendanceListId(Long attendanceListId) {
        this.attendanceListId = attendanceListId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAttendanceListName() {
        return attendanceListName;
    }

    public void setAttendanceListName(String attendanceListName) {
        this.attendanceListName = attendanceListName;
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

    public LocalDateTime getPresenceAt() {
        return presenceAt;
    }

    public void setPresenceAt(LocalDateTime presenceAt) {
        this.presenceAt = presenceAt;
    }
}
