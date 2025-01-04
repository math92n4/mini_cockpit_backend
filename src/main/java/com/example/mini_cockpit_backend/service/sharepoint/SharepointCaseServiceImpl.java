package com.example.mini_cockpit_backend.service.sharepoint;

import com.example.mini_cockpit_backend.api.SharepointAPIService;
import com.example.mini_cockpit_backend.dto.SharepointCaseDTO;
import com.example.mini_cockpit_backend.entity.*;
import com.example.mini_cockpit_backend.service.brand.BrandService;
import com.example.mini_cockpit_backend.service.casetype.CaseTypeService;
import com.example.mini_cockpit_backend.service.customer.CustomerService;
import com.example.mini_cockpit_backend.service.insurance.InsuranceService;
import com.example.mini_cockpit_backend.service.isvr.IvsrService;
import com.example.mini_cockpit_backend.service.isvr.IvsrServiceImpl;
import com.example.mini_cockpit_backend.service.model.ModelService;
import com.example.mini_cockpit_backend.service.salesperson.SalesPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SharepointCaseServiceImpl implements SharepointCaseService {

    private final SharepointAPIService sharepointAPIService;
    private final IvsrService ivsrService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final InsuranceService insuranceService;
    private final CaseTypeService caseTypeService;
    private final CustomerService customerService;
    private final SalesPersonService salesPersonService;

    @Override
    public void saveSharepointData() {
        List<SharepointCaseDTO> dtos = sharepointAPIService.getCaseData();

        for (SharepointCaseDTO sharepointDto : dtos) {

            Ivsr ivsr;
            String productionNumber = sharepointDto.getTitle();

            if (ivsrService.doesExist(productionNumber)) {
                ivsr = ivsrService.getByProductionNumber(productionNumber);
            } else {
                ivsr = new Ivsr();
                ivsr.setProductionNumber(productionNumber);
            }

            Customer customer = customerService.findByEmail(sharepointDto.getCustomerMail());

            if (customer == null) {
                customer = new Customer();
                customer.setEmail(sharepointDto.getCustomerMail());
                customer.setZip(sharepointDto.getZip());
                customer.setName(sharepointDto.getCustomerName());
                customerService.save(customer);
            }

            SalesPerson salesPerson = salesPersonService.findById(sharepointDto.getSalesPersonId());

            if (salesPerson == null) {
                salesPerson = new SalesPerson();
                salesPerson.setId(sharepointDto.getSalesPersonId());
                salesPerson.setName(sharepointDto.getSalesPersonName());
                salesPersonService.save(salesPerson);
            }

            String brandName = "";

            if (sharepointDto.getModel().toUpperCase().contains("MINI") ||
                    sharepointDto.getModel().toUpperCase().contains("COOP") ||
                    sharepointDto.getModel().toUpperCase().contains("COUNT")) {

                brandName = "MINI";

            } else {
                brandName = sharepointDto.getManufacturer();
            }

            Brand brand = brandService.findByName(brandName);

            if (brand == null) {
                brand = new Brand();
                brand.setName(sharepointDto.getManufacturer());
                brand = brandService.save(brand);
            }

            Model model = modelService.find(sharepointDto.getModelCode(), sharepointDto.getModel());


            if (model == null) {
                model = new Model();
                model.setBrand(brand);
                model.setModelDescription(sharepointDto.getModel());
                model = modelService.save(model);
            }

            CaseType caseType = caseTypeService.findByName(sharepointDto.getCaseType());

            if (caseType == null) {
                caseType = new CaseType();
                caseType.setName(sharepointDto.getCaseType());
                caseType = caseTypeService.save(caseType);
            }

            Insurance insurance = insuranceService.findByName(sharepointDto.getInsurance());

            if (insurance == null) {
                insurance = new Insurance();
                insurance.setName(sharepointDto.getInsurance());
                insurance = insuranceService.save(insurance);
            }

            ivsr.setModel(model);
            ivsr.setCaseType(caseType);
            ivsr.setInsurance(insurance);
            ivsr.setCustomer(customer);
            ivsr.setSalesPerson(salesPerson);

            ivsr.setSharepointData(true);

            ivsrService.save(ivsr);

            }

    }

    @Override
    public List<SharepointCaseDTO> getSharePointData() {
        return sharepointAPIService.getCaseData();
    }

}
