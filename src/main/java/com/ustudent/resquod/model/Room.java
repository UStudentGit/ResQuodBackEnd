package com.ustudent.resquod.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Corporation corporation;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    private final Set<Position> positions = new HashSet<>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "room")
    private final Set<Event> event = new HashSet<>();
    public Set<Position> getPositions() { return positions; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Corporation getCorporation() {
        return corporation;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

}
