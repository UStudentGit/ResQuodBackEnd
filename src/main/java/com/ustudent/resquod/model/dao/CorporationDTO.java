package com.ustudent.resquod.model.dao;

public class CorporationDTO {
    private Long id;
    private String name;

    public CorporationDTO() {
    }

    public CorporationDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
