package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Room;
import com.ustudent.resquod.model.dao.EventDTO;
import com.ustudent.resquod.model.dao.RoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Optional<Room> findByName(String name);

    Optional<Room> findById(Long Id);

    @Query(value = "SELECT r FROM Room r " +
            "INNER JOIN Corporation c ON c.id = r.corporation.id " +
            "JOIN c.users u " +
            "WHERE r.id = ?1 AND u.email = ?2")
    Optional<Room> findByRoomIdAndOwnerEmail(Long id, String email);

    @Query(value = "SELECT new com.ustudent.resquod.model.dao.RoomDTO(r.id,r.name) " +
            "FROM  Room r JOIN Corporation c ON r.corporation.id=c.id  " +
            "WHERE c.id = ?1")
    Set<RoomDTO> findByCorporationId(Long id);

    @Query(value = "DELETE FROM Room r WHERE r.id=?1")
    void removeRoomById(Long Id);

    @Override
    void delete(Room room);
}
