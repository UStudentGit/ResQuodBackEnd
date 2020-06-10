package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.ObjectNotFoundException;
import com.ustudent.resquod.model.Presence;
import com.ustudent.resquod.model.dao.PresenceData;
import com.ustudent.resquod.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceService {

    private final PresenceRepository presenceRepository;

    @Autowired
    PresenceService(PresenceRepository presenceRepository) {
        this.presenceRepository = presenceRepository;
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
}
