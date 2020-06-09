package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Presence;
import com.ustudent.resquod.model.dao.PresenceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Integer> {
    @Query(value = "SELECT new com.ustudent.resquod.model.dao.PresenceData(p.id, p.presence, p.date, p.user.id, p.attendanceList.id) " +
            "FROM Presence p " +
            "INNER JOIN AttendanceList a ON a.id = p.attendanceList.id " +
            "INNER JOIN User u ON u.id = p.user.id " +
            "WHERE u.email = ?1")
    List<PresenceData> findPresencesByUserEmail(String email);

    @Query(value = "SELECT p from Presence p " +
            "JOIN User u ON u.id=p.user.id " +
            "JOIN AttendanceList a ON a.id=p.attendanceList.id " +
            "JOIN Event e ON e.id=a.event.id " +
            "JOIN Room r ON r.id=e.room.id " +
            "JOIN Position p2 ON p2.room.id=r.id " +
            "WHERE u.id=?1 AND p2.tagId= ?2 AND a.startTime <= ?3 order by a.startTime desc")
    Optional<List<Presence>> findByDateAfter(Long userId, String nfcTag, LocalDateTime date);
}
