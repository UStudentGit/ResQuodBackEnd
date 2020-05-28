package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.AttendanceListCreateException;
import com.ustudent.resquod.model.AttendanceList;
import com.ustudent.resquod.model.dao.AttendanceListData;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.repository.AttendanceListRepository;
import com.ustudent.resquod.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceListService {

    @Autowired
    private AttendanceListRepository attendanceListRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    AttendanceListService(AttendanceListRepository attendanceListRepository) {
        this.attendanceListRepository = attendanceListRepository;
    }

    public List<AttendanceListData> findUserAttendanceLists(String email){
        return attendanceListRepository.findListsByUserEmail(email);
    }

    public void createAttendanceList(Long eventId, String string){
        eventRepository.findById(eventId).map(
                event -> {
                    AttendanceList attendanceList = new AttendanceList(event, string);
                    return attendanceListRepository.save(attendanceList);
                }
        ).orElseThrow(() -> new AttendanceListCreateException());
    }

    public AttendanceList getAttendanceList(Long eventId){
        AttendanceList attendanceList = attendanceListRepository.findByEventId(eventId);
        return attendanceList;
    }

    public List<UserData> getPresentUsers(Long attendanceListId){
        return attendanceListRepository.findPresentUsers(attendanceListId);

    }
    public List<UserData> getAbsentUsers(Long attendanceListId){
        return attendanceListRepository.findAbsentUsers(attendanceListId);

    }
}
