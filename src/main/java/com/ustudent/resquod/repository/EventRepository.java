package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.Optional;



public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findById(Long id);

    Optional<Event> findByPassword(String password);

    List<Event> findAll();

    Set<Event> findByAdministratorId(Long id);

    @Query(value = "SELECT e FROM Event e " +
            "LEFT JOIN Room r ON r.id = e.room.id " +
            "where e.name = ?1 and e.room.id = ?2")
    Optional<Event> findByName(String name, Long roomId);

    @Query(value = "SELECT e FROM Event e " +
            "INNER JOIN Room r ON r.id = e.room.id and r.corporation.id = ?1"
    )
    List<Event> findByCorpoId(Long corpoId);
}
