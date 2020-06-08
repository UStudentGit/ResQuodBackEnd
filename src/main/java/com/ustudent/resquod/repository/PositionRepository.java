package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Long> {

    @Query(value = "SELECT p FROM Position p " +
            "LEFT JOIN Room r ON r.id = p.room.id " +
            "where p.numberOfPosition = ?1 and p.room.id = ?2")
    Optional<Position> findByNumberOfPosition(Integer numberOfPosition, Long roomId);

    @Query(value = "SELECT p FROM Position p " +
            "INNER JOIN Room r ON r.id = p.room.id " +
            "INNER JOIN Corporation c ON c.id = r.corporation.id " +
            "JOIN c.users u " +
            "WHERE p.id = ?1 AND u.email = ?2")
    Optional<Position> findByIdAndEmail(Long id, String email);
}
