package com.ustudent.resquod.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events",indexes = @Index(columnList = "password"))
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long administratorId;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Room room;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "Events_Users",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private  Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "event")
    private List<AttendanceList> attendanceLists;



    public Event() { }

    public Event(Long id, String name, Long administratorId, String password, Room room, Set<User> users) {
        this.id = id;
        this.name = name;
        this.administratorId = administratorId;
        this.password = password;
        this.room = room;
        this.users = users;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
    }

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<AttendanceList> getAttendanceLists() {
        return attendanceLists;
    }

    public void setAttendanceLists(List<AttendanceList> attendanceLists) {
        this.attendanceLists = attendanceLists;
    }

}
