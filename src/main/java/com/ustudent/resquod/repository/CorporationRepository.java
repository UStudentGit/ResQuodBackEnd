package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorporationRepository extends JpaRepository<Corporation,Long> {
    Optional<Corporation> findById(Long id);
    Optional<Corporation> findByName(String name);
    List<Corporation> findAll();
}
