package com.example.mini_cockpit_backend.service.casetype;

import com.example.mini_cockpit_backend.entity.CaseType;
import com.example.mini_cockpit_backend.repository.CaseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseTypeImpl implements CaseTypeService {

    private final CaseTypeRepository caseTypeRepository;

    @Override
    public CaseType save(CaseType caseType) {
        return caseTypeRepository.save(caseType);
    }

    @Override
    public CaseType findByName(String name) {
        return caseTypeRepository.findByName(name);
    }
}
