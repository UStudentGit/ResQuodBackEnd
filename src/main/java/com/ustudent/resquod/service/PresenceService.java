package com.ustudent.resquod.service;

import com.ustudent.resquod.model.dao.PresenceData;
import com.ustudent.resquod.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresenceService {

    private PresenceRepository presenceRepository;

    @Autowired
    PresenceService(PresenceRepository presenceRepository) {
        this.presenceRepository = presenceRepository;
    }

    public List<PresenceData> findUserPresences(String email){
        return presenceRepository.findPresencesByUserEmail(email);
    }
}
