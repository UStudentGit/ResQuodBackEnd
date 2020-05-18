package com.ustudent.resquod.model.dao;

import java.time.LocalDateTime;

public class AttendanceListData {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private Long event_id;

    public AttendanceListData(Long id, String name, LocalDateTime createTime, Long event_id){
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.event_id = event_id;
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

    public Long getEventID() {
        return event_id;
    }

    public void setEventID(Long event_id) {
        this.event_id = event_id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}