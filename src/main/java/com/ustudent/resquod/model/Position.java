package com.ustudent.resquod.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer numberOfPosition;
    @UniqueElements
    private String tagId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getNumberOfPosition() {
        return numberOfPosition;
    }

    public void setNumberOfPosition(Integer numberOfPosition) {
        this.numberOfPosition = numberOfPosition;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
