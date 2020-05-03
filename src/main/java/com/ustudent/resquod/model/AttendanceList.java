package com.ustudent.resquod.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendanceList")
public class AttendanceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToOne
    private Event event;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "Attendancelists_Users",
            joinColumns = @JoinColumn(name = "attendanceList_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private final List<User> users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
