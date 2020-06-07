package com.ustudent.resquod.model;

import javax.persistence.*;

@Entity
@Table(name = "positions", uniqueConstraints={@UniqueConstraint(columnNames = {"tagId"})})
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfPosition;
    private String tagId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
