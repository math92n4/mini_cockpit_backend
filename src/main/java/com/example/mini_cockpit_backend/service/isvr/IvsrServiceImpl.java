package com.example.mini_cockpit_backend.service.isvr;

import com.example.mini_cockpit_backend.api.dto.IvsrDTO;
import com.example.mini_cockpit_backend.entity.*;
import com.example.mini_cockpit_backend.repository.IvsrRepository;
import com.example.mini_cockpit_backend.service.brand.BrandService;
import com.example.mini_cockpit_backend.service.customer.CustomerService;
import com.example.mini_cockpit_backend.service.model.ModelService;
import com.example.mini_cockpit_backend.service.salesperson.SalesPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IvsrServiceImpl implements IvsrService {

    private final IvsrRepository ivsrRepository;
    private final ModelService modelService;
    private final BrandService brandService;
    private final CustomerService customerService;
    private final SalesPersonService salesPersonService;

    @Override
    public void saveList(List<Ivsr> list) {
        ivsrRepository.saveAll(list);
    }

    @Override
    public List<IvsrDTO> getAll() {
        List<Ivsr> ivsrList = ivsrRepository.findAll();
        List<IvsrDTO> ivsrDtos = new ArrayList<>();

        for (Ivsr ivsr : ivsrList) {
            IvsrDTO ivsrDTO = new IvsrDTO();

            if (ivsr.getModel() != null) {
                ivsrDTO.setModelCode(ivsr.getModel().getModelCode());
                ivsrDTO.setModelDescription(ivsr.getModel().getModelDescription());
                if (ivsr.getModel().getBrand() != null) {
                    ivsrDTO.setBrand(ivsr.getModel().getBrand().getName());
                }
            }

            if (ivsr.getCustomer() != null) {
                //ivsrDTO.setCustomerName(ivsr.getCustomer().getName());
                //ivsrDTO.setCustomerMail(ivsr.getCustomer().getEmail());
                ivsrDTO.setZip(ivsr.getCustomer().getZip());
            }

            if (ivsr.getSalesPerson() != null) {
                ivsrDTO.setSalesPerson(ivsr.getSalesPerson().getName());
            }

            ivsrDTO.setProductionNumber(ivsr.getProductionNumber());
            ivsrDTO.setColorCode(ivsr.getColorCode());
            ivsrDTO.setOptionsString(ivsr.getOptionsString());
            ivsrDTO.setActualProductionDate(ivsr.getActualProductionDate());
            ivsrDTO.setPurchaseAgreementDate(ivsr.getPurchaseAgreementDate());
            ivsrDTO.setRetailCountingDate(ivsr.getRetailCountingDate());
            ivsrDTO.setAgreementDate(ivsr.getPurchaseAgreementDate());
            ivsrDTO.setPlannedHandoverWeek(ivsr.getPlannedHandoverWeek());
            ivsrDTO.setExpectedDeliveryWeek(ivsr.getExpectedDeliveryWeek());
            ivsrDTO.setSharepointData(ivsr.isSharepointData());
            ivsrDTO.setIvsrData(ivsr.isIvsrData());
            ivsrDtos.add(ivsrDTO);
        }
        return ivsrDtos;
    }

    @Override
    public boolean doesExist(String productionNumber) {
        return ivsrRepository.existsById(productionNumber);
    }

    @Override
    public Ivsr getByProductionNumber(String productionNumber) {
        return ivsrRepository.getIvsrByProductionNumber(productionNumber);
    }

    @Override
    public void save(Ivsr ivsr) {
        ivsrRepository.save(ivsr);
    }

    @Override
    public void deleteByProductionNumber(String productionNumber) {
        ivsrRepository.deleteById(productionNumber);
    }

    @Override
    public IvsrDTO updateCar(IvsrDTO ivsrDTO) {
        Ivsr ivsr = getByProductionNumber(ivsrDTO.getProductionNumber());

        String brandName = ivsrDTO.getBrand();
        if (brandName == null || brandName.trim().isEmpty()) {
            brandName = "Ikke sat";
        }

        Brand brand = brandService.findByName(brandName);

        if (brand == null) {
            brand = new Brand();
            brand.setName(ivsrDTO.getBrand());
            brand = brandService.save(brand);
        } else {
            brand.setName(ivsrDTO.getBrand());
        }

        String modelCode = ivsrDTO.getModelCode();

        if(modelCode == null || modelCode.trim().isEmpty()) {
            modelCode = "Ikke sat";
        }

        String modelDesc = ivsrDTO.getModelDescription();

        if(modelDesc == null || modelDesc.trim().isEmpty()) {
            modelDesc = "Ikke sat";
        }

        Model model = modelService.find(modelCode, modelDesc);

        if (model == null) {
            model = new Model();
        }

        model.setBrand(brand);
        model.setModelCode(modelCode);
        model.setModelDescription(modelDesc);
        model = modelService.save(model);

        /*
        Customer customer = customerService.findByEmail(ivsrDTO.getCustomerMail());


        if(customer == null) {
            customer = new Customer();
            customer.setEmail(ivsrDTO.getCustomerMail());
        }

        customer.setName(ivsrDTO.getCustomerName());


        customer.setZip(ivsrDTO.getZip());
        customerService.save(customer);
        */

        ivsr.setActualProductionDate(ivsrDTO.getActualProductionDate());
        ivsr.setColorCode(ivsrDTO.getColorCode());
        ivsr.setExpectedDeliveryWeek(ivsrDTO.getExpectedDeliveryWeek());
        ivsr.setOptionsString(ivsrDTO.getOptionsString());
        ivsr.setPlannedHandoverWeek(ivsrDTO.getPlannedHandoverWeek());
        ivsr.setPurchaseAgreementDate(ivsrDTO.getPurchaseAgreementDate());
        ivsr.setRetailCountingDate(ivsrDTO.getRetailCountingDate());


        ivsr.setModel(model);
        //ivsr.setCustomer(customer);

        save(ivsr);

        return ivsrDTO;
    }




}
