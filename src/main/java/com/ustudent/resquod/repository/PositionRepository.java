package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Long> {

    Optional<Position> findByNumberOfPosition(Integer numberOfPosition);

    @Query(value = "SELECT p FROM Position p " +
            "INNER JOIN Room r ON r.id = p.room.id " +
            "INNER JOIN Corporation c ON c.id = r.corporation.id " +
            "JOIN c.users u " +
            "WHERE p.Id = ?1 AND u.email = ?2")
    Optional<Position> findByIdAndEmail(Long Id, String email);
}
