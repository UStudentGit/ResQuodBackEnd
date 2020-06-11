package com.ustudent.resquod.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class PresenceData {
    private Long id;
    private Boolean presence;
    @JsonIgnore
    private LocalDateTime date;
    private Long userId;
    private Long attendanceListId;

    public PresenceData(Long id, Boolean presence, LocalDateTime date, Long userId, Long attendanceListId){
        this.id = id;
        this.presence = presence;
        this.date = date;
        this.userId = userId;
        this.attendanceListId = attendanceListId;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPresence() {
        return presence;
    }

    public void setPresence(Boolean presence) {
        this.presence = presence;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserID() {
        return userId;
    }

    public void setUser(Long user_id) {
        this.userId = user_id;
    }

    public Long getAttendanceListID() { return attendanceListId; }

    public void setAttendanceList(Long attendance_list_id) {
        this.attendanceListId = attendance_list_id;
    }

}
