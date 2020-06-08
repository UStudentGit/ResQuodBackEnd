package com.ustudent.resquod.validator;

import com.ustudent.resquod.model.Corporation;
import org.springframework.stereotype.Component;

@Component
public class CorporationValidator {

    public boolean validateCorporation(Corporation corporationToValidate){
        return corporationToValidate.getName() != null && !corporationToValidate.getName().isEmpty();
    }
}
