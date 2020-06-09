package com.ustudent.resquod.service;


import com.ustudent.resquod.exception.EventNotFoundException;
import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.model.AttendanceList;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.dao.AttendanceListData;
import com.ustudent.resquod.model.dao.UserData;
import com.ustudent.resquod.repository.AttendanceListRepository;
import com.ustudent.resquod.repository.EventRepository;
import com.ustudent.resquod.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceListService {

    private AttendanceListRepository attendanceListRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    AttendanceListService(AttendanceListRepository attendanceListRepository) {
        this.attendanceListRepository = attendanceListRepository;
    }

    public List<AttendanceListData> findUserAttendanceLists(String email) {
        return attendanceListRepository.findListsByUserEmail(email);
    }

    public void createAttendanceList(AttendanceListData attData) {
        Event event = eventRepository.findById(attData.getEventId()).orElseThrow(EventNotFoundException::new);
        if(!userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get().getId().
                equals(event.getAdministratorId()))
            throw new PermissionDeniedException();
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.setName(attData.getName());
        attendanceList.setStartTime(attData.getStartTime());
        attendanceList.setEndTime(attData.getEndTime());
        attendanceList.setEvent(event);
        attendanceListRepository.save(attendanceList);
    }


    public List<AttendanceList> getAttendanceList(Long eventId) {
        List<AttendanceList> attendanceList = attendanceListRepository.findByEventId(eventId);
        return attendanceList;
    }

    public List<UserData> getPresentUsers(Long attendanceListId) {
        return attendanceListRepository.findPresentUsers(attendanceListId);

    }

    public List<UserData> getAbsentUsers(Long attendanceListId) {
        return attendanceListRepository.findAbsentUsers(attendanceListId);

    }

}
