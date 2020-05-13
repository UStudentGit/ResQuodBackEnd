package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
