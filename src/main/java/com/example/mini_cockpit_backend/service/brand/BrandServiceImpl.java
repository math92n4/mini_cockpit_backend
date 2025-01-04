package com.example.mini_cockpit_backend.service.brand;

import com.example.mini_cockpit_backend.entity.Brand;
import com.example.mini_cockpit_backend.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand getById(int id) {
        return brandRepository.getReferenceById(id);
    }

    @Override
    public Brand save(Brand brand) {
        String brandName = brand.getName().trim().toUpperCase();

        if (brandName.contains("MI")) {
            brandName = "MINI";
        }
        Brand savedBrand = new Brand();
        savedBrand.setName(brandName);
        return brandRepository.save(savedBrand);
    }

    @Override
    public Brand findByName(String name) {
        String brandName = name.toUpperCase().trim();
        if (brandName.contains("MI")) {
            brandName = "MINI";
        }
        return brandRepository.findByName(brandName);
    }
}
