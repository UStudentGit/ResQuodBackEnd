package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.CorporationNotFoundException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorporationService {

    private final CorporationRepository corporationRepository;

    @Autowired
    public CorporationService(CorporationRepository corporationRepository) {
        this.corporationRepository=corporationRepository;
    }

    public Corporation getCorpoById(Corporation corporationToValidate) {
        return corporationRepository.findById(corporationToValidate.getId()).orElseThrow(CorporationNotFoundException::new);
    }
}