package com.example.mini_cockpit_backend.service.sharepoint;

import com.example.mini_cockpit_backend.api.ApiService;
import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.entity.Brand;
import com.example.mini_cockpit_backend.entity.Ivsr;
import com.example.mini_cockpit_backend.entity.Model;
import com.example.mini_cockpit_backend.service.brand.BrandService;
import com.example.mini_cockpit_backend.service.file.FileService;
import com.example.mini_cockpit_backend.service.isvr.IvsrService;
import com.example.mini_cockpit_backend.service.model.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IvsrSharepointServiceImpl implements IvsrSharepointService {

    private final ApiService apiService;
    private final IvsrService ivsrService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final FileService fileService;

    @Override
    public void saveIvsrData(List<IvsrDTO> dtos) {
        for (IvsrDTO ivsrDTO : dtos) {
            System.out.println(ivsrDTO.getModelCode());
            Ivsr ivsr;
            String productionNumber = ivsrDTO.getProductionNumber();

            if (ivsrService.doesExist(productionNumber)) {
                ivsr = ivsrService.getByProductionNumber(productionNumber);
            } else {
                ivsr = new Ivsr();
                ivsr.setProductionNumber(productionNumber);
            }

            Brand brand = brandService.findByName(ivsrDTO.getBrand());

            if (brand == null) {
                brand = new Brand();
                brand.setName(ivsrDTO.getBrand());
                brand = brandService.save(brand);
            }

            Model model = modelService.find(ivsrDTO.getModelCode(), ivsrDTO.getModelDescription());

            if (model == null) {
                model = new Model();
                model.setBrand(brand);
                model.setModelCode(ivsrDTO.getModelCode());
                model.setModelDescription(ivsrDTO.getModelDescription());
                model = modelService.save(model);
            }

            ivsr.setActualProductionDate(ivsrDTO.getActualProductionDate());
            ivsr.setColorCode(ivsrDTO.getColorCode());
            //ivsr.setCustomerLast(ivsrDTO.getCustomerLast());
            ivsr.setExpectedDeliveryWeek(ivsrDTO.getExpectedDeliveryWeek());
            ivsr.setOptionsString(ivsrDTO.getOptionsString());
            ivsr.setPlannedHandoverWeek(ivsrDTO.getPlannedHandoverWeek());
            ivsr.setPurchaseAgreementDate(ivsrDTO.getPurchaseAgreementDate());
            ivsr.setRetailCountingDate(ivsrDTO.getRetailCountingDate());
            ivsr.setIvsrData(true);


            ivsr.setModel(model);


            ivsrService.save(ivsr);
        }

    }

}
