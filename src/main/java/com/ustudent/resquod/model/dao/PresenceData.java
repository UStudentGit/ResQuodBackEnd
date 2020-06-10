package com.ustudent.resquod.model.dao;

import java.time.LocalDateTime;
import java.util.Date;

public class PresenceData {
    private Long id;
    private Boolean presence;
    private LocalDateTime date;
    private Long user_id;
    private Long attendance_list_id;

    public PresenceData(Long id, Boolean presence, LocalDateTime date, Long user_id, Long attendance_list_id){
        this.id = id;
        this.presence = presence;
        this.date = date;
        this.user_id = user_id;
        this.attendance_list_id = attendance_list_id;
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
        return user_id;
    }

    public void setUser(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAttendanceListID() { return attendance_list_id; }

    public void setAttendanceList(Long attendance_list_id) {
        this.attendance_list_id = attendance_list_id;
    }

}
