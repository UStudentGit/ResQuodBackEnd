package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.dao.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findById(Long id);

    Optional<String> findByPassword(String password);


    @Query(value = "SELECT new com.ustudent.resquod.model.dao.EventDTO(e.id,e.name,e.administratorId,e.password,r.id,r.name) " +
            "FROM  Event e JOIN Room r ON r.id=e.room.id  " +
            "WHERE e.administratorId = ?1")
    Set<EventDTO> findByAdministratorId(Long id);



}
