package com.ustudent.resquod.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="corporations")
public class Corporation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "Corporations_Users",
            joinColumns = @JoinColumn(name = "corporations_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private final Set<User> users = new HashSet<>();

    public Corporation() {
    }

    public Corporation(String name) {
        this.name = name;
    }

    public void addUser(User user){
        users.add(user);
        user.getCorporations().add(this);
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

    public Set<User> getUsers() {
        return users;
    }
}
