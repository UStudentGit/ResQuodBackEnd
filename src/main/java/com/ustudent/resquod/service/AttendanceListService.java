package com.ustudent.resquod.service;

import com.ustudent.resquod.repository.AttendanceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ustudent.resquod.model.dao.AttendanceListData;

import java.util.List;

@Service
public class AttendanceListService {

    private AttendanceListRepository attendanceListRepository;

    @Autowired
    AttendanceListService(AttendanceListRepository attendanceListRepository) {
        this.attendanceListRepository = attendanceListRepository;
    }

    public List<AttendanceListData> findUserAttendanceLists(String email){
        return attendanceListRepository.findListsByUserEmail(email);
    }
}
