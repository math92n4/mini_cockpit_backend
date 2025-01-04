package com.example.mini_cockpit_backend.service.salesperson;

import com.example.mini_cockpit_backend.entity.SalesPerson;
import com.example.mini_cockpit_backend.repository.SalesPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesPersonServiceImpl implements SalesPersonService {

    private final SalesPersonRepository salesPersonRepository;

    @Override
    public SalesPerson findById(int id) {
        return salesPersonRepository.findById(id).orElse(null);
    }

    @Override
    public void save(SalesPerson salesPerson) {
        salesPersonRepository.save(salesPerson);
    }
}
