package com.ustudent.resquod.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
@Table(name = "positions", uniqueConstraints={@UniqueConstraint(columnNames = {"tag_id"})})
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer numberOfPosition;
    private String tag_id;
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
        return tag_id;
    }

    public void setTagId(String tag_id) {
        this.tag_id = tag_id;
    }
}
