package com.example.mini_cockpit_backend.service.insurance;

import com.example.mini_cockpit_backend.entity.Insurance;

public interface InsuranceService {

    Insurance save(Insurance insurance);
    Insurance findByName(String name);
}
