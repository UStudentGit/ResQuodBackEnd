package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Optional<Room> findByName(String name);

    Optional<Room> findById(Long Id);

    @Query(value = "SELECT r FROM Room r " +
            "INNER JOIN Corporation c ON c.id = r.corporation.id " +
            "JOIN c.users u " +
            "WHERE r.id = ?1 AND u.email = ?2")
    Optional<Room> findByRoomIdAndOwnerEmail(Long id, String email);
}
