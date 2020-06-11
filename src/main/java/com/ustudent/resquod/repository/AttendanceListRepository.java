package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.AttendanceList;
import com.ustudent.resquod.model.dao.AttendanceListData;
import com.ustudent.resquod.model.dao.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceListRepository extends JpaRepository<AttendanceList,Integer> {
    @Query(value = "SELECT new com.ustudent.resquod.model.dao.AttendanceListData(a.id, a.name, a.startTime, a.endTime, a.event.id) " +
            "FROM AttendanceList a " +
            "INNER JOIN Presence p ON a.id = p.attendanceList.id " +
            "INNER JOIN User u ON u.id = p.user.id " +
            "WHERE u.email = ?1")
    List<AttendanceListData> findListsByUserEmail(String email);


    AttendanceList findById(Long Id);

    List<AttendanceList> findByEventId(Long eventId);

    boolean existsByEventId(long eventId);


    @Query(value = "SELECT new com.ustudent.resquod.model.dao.UserData(u.email, u.role, u.name, u.surname) " +
            " FROM User u " +
            " INNER JOIN Presence p ON u.id = p.user.id " +
            " WHERE p.attendanceList.id=?1 " +
            " AND p.presence='1'")
    List<UserData> findPresentUsers(Long attendanceListId);

    @Query(value = "SELECT new com.ustudent.resquod.model.dao.UserData(u.email, u.role, u.name, u.surname) " +
            " FROM User u " +
            " INNER JOIN Presence p ON u.id = p.user.id " +
            " WHERE p.attendanceList.id=?1 " +
            " AND p.presence='0'")
    List<UserData> findAbsentUsers(Long attendanceListId);




}

