package com.ustudent.resquod.model.dao;

public class NewRoomData {

    private Long corporationId;
    private String name;
    private Long id;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(Long corporationId) {
        this.corporationId = corporationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
