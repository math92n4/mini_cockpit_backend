package com.example.mini_cockpit_backend.service.brand;

import com.example.mini_cockpit_backend.entity.Brand;

public interface BrandService {

    Brand getById(int id);
    Brand save(Brand brand);
    Brand findByName(String name);

}
