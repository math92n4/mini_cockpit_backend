package com.example.mini_cockpit_backend.service.model;

import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.entity.Brand;
import com.example.mini_cockpit_backend.entity.Model;
import com.example.mini_cockpit_backend.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Model find(String modelCode, String modelDescription) {
        if (modelCode != null && !modelCode.isEmpty()) {
            Model model = modelRepository.findByModelCode(modelCode);

            if (model != null) {
                return model;
            }
        }

        if (modelDescription != null && !modelDescription.isEmpty()) {
            Model model = modelRepository.findByModelDescription(modelDescription);

            if (model != null) {
                return model;
            }
        }

        return null;
    }


}
