package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Presence;
import com.ustudent.resquod.model.dao.PresenceData;
import com.ustudent.resquod.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private UserService userService;
    private AttendanceListService attendanceListService;

    @Lazy
    @Autowired
    PresenceService(PresenceRepository presenceRepository, UserService userService, AttendanceListService attendanceListService) {
        this.presenceRepository = presenceRepository;
        this.userService = userService;
        this.attendanceListService = attendanceListService;
    }

    public List<PresenceData> findUserPresences(String email) {
        return presenceRepository.findPresencesByUserEmail(email);
    }

    public Presence getPresence(String tagId, LocalDateTime date, Long id) {
        Presence presence = presenceRepository.findByDateAfter(id, tagId, date).orElseThrow(ObjectNotFoundException::new).get(0);
        if (presence.getDate()==null){
            presence.setDate(date);
            presence.setPresence(true);
            presenceRepository.save(presence);
        }
        return presence;
    }

    public void createPresence(Long userId, Long attendanceListId){
        Presence presence = new Presence();
        presence.setUser(userService.getUserById(userId));
        presence.setPresence(false);
        presence.setAttendanceList(attendanceListService.getAttendanceListById(attendanceListId));
        presenceRepository.save(presence);
    }
}
