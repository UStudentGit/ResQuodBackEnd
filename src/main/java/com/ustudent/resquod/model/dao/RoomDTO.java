package com.ustudent.resquod.model.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDTO {
    private Long id;
    private String name;
    @JsonProperty
    private CorporationDTO corporation;


    public RoomDTO(Long id, String name, CorporationDTO corporation) {
        this.id = id;
        this.name = name;
        this.corporation = corporation;
    }

    public CorporationDTO getCorporation() {
        return corporation;
    }

    public void setCorporation(CorporationDTO corporation) {
        this.corporation = corporation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
