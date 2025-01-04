package com.example.mini_cockpit_backend.service.insurance;

import com.example.mini_cockpit_backend.entity.Insurance;
import com.example.mini_cockpit_backend.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Override
    public Insurance save(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    @Override
    public Insurance findByName(String name) {
        return insuranceRepository.findByName(name);
    }
}
