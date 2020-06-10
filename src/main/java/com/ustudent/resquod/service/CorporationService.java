package com.ustudent.resquod.service;

import com.ustudent.resquod.exception.CorporationNotFoundException;
import com.ustudent.resquod.exception.InvalidInputException;
import com.ustudent.resquod.model.Corporation;
import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.CorpoData;
import com.ustudent.resquod.repository.CorporationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CorporationService {

    private final CorporationRepository corporationRepository;

    @Autowired
    public CorporationService(CorporationRepository corporationRepository) {
        this.corporationRepository=corporationRepository;
    }

    public Corporation getCorpoById(Long corporationId) {
        return corporationRepository.findById(corporationId).orElseThrow(CorporationNotFoundException::new);
    }

    public void addNewCorpo(CorpoData inputData) throws InvalidInputException {
        if(inputData.getName()==null || inputData.getName().length() <2)
            throw new InvalidInputException();
        corporationRepository.save(new Corporation(inputData.getName()));
    }

    public List<CorpoData> showEveryCorpo() {
        List<Corporation> events = corporationRepository.findAll();
        List<CorpoData> allEvents = new ArrayList<>();
        for (Corporation e: events){
            allEvents.add(new CorpoData(e.getId(),e.getName()));
        }
        return allEvents;
    }
}