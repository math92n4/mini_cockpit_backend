package com.example.mini_cockpit_backend.service.casetype;

import com.example.mini_cockpit_backend.entity.CaseType;

public interface CaseTypeService {

    CaseType save(CaseType caseType);
    CaseType findByName(String name);

}
