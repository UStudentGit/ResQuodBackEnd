package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.AttendanceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ustudent.resquod.model.dao.AttendanceListData;

import java.util.List;

@Repository
public interface AttendanceListRepository extends JpaRepository<AttendanceList,Integer> {
    @Query(value = "SELECT new com.ustudent.resquod.model.dao.AttendanceListData(a.id, a.name, a.createTime, a.event.id) " +
            "FROM AttendanceList a " +
            "INNER JOIN Presence p ON a.id = p.attendanceList.id " +
            "INNER JOIN User u ON u.id = p.user.id " +
            "WHERE u.email = ?1")
    List<AttendanceListData> findListsByUserEmail(String email);
}
