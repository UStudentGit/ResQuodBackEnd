package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    Optional<Room> findByName(String name);
}
