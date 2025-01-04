package com.example.mini_cockpit_backend.service.salesperson;

import com.example.mini_cockpit_backend.entity.SalesPerson;

public interface SalesPersonService {

    SalesPerson findById(int id);
    void save(SalesPerson salesPerson);

}
