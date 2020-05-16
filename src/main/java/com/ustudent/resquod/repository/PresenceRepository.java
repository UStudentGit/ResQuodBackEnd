package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ustudent.resquod.model.dao.PresenceData;

import java.util.List;

@Repository
public interface PresenceRepository  extends JpaRepository<Presence,Integer> {
    @Query(value = "SELECT new com.ustudent.resquod.model.dao.PresenceData(p.id, p.presence, p.date, p.user.id, p.attendanceList.id) " +
            "FROM Presence p " +
            "INNER JOIN AttendanceList a ON a.id = p.attendanceList.id " +
            "INNER JOIN User u ON u.id = p.user.id " +
            "WHERE u.email = ?1")
    List<PresenceData> findPresencesByUserEmail(String email);
}
