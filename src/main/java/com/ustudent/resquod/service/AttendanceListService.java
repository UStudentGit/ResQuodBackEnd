package com.ustudent.resquod.service;


import com.ustudent.resquod.exception.EmailExistException;
import com.ustudent.resquod.exception.EventNotFoundException;
import com.ustudent.resquod.exception.PermissionDeniedException;
import com.ustudent.resquod.exception.WrongTimeFrameException;
import com.ustudent.resquod.model.AttendanceList;
import com.ustudent.resquod.model.Event;
import com.ustudent.resquod.model.User;
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
    private PresenceService presenceService;
    private EventRepository eventRepository;
    private UserRepository userRepository;


    @Autowired
    AttendanceListService(AttendanceListRepository attendanceListRepository, PresenceService presenceService, EventRepository eventRepository, UserRepository userRepository) {
        this.attendanceListRepository = attendanceListRepository;
        this.presenceService = presenceService;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<AttendanceListData> findUserAttendanceLists() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        UserData userData = userService.getUser(email);
        return attendanceListRepository.findListsByUserEmail(email);
    }

    public void createAttendanceList(AttendanceListData attData) {
        if(attData.getEndTime().isBefore(attData.getStartTime()))
            throw new WrongTimeFrameException();
        Event event = eventRepository.findById(attData.getEventId()).orElseThrow(EventNotFoundException::new);
        if(!userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get().getId().
                equals(event.getAdministratorId()) &&
                !userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).get().getRole().equals("ROLE_ADMIN"))
            throw new PermissionDeniedException();
        AttendanceList attendanceList = new AttendanceList();
        attendanceList.setName(attData.getName());
        attendanceList.setStartTime(attData.getStartTime());
        attendanceList.setEndTime(attData.getEndTime());
        attendanceList.setEvent(event);
        attendanceListRepository.save(attendanceList);
        for (User user : event.getUsers()) {
            presenceService.createPresence(user.getId(), attendanceList.getId());
        }
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
    public AttendanceList getAttendanceListById(Long Id) throws EmailExistException {
        return attendanceListRepository.findById(Id);
    }

}
