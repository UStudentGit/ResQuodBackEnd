package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Long> {

    Optional<Position> findByNumberOfPosition(Integer numberOfPosition);
}
