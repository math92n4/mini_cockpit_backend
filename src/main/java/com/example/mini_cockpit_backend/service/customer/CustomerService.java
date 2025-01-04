package com.example.mini_cockpit_backend.service.customer;

import com.example.mini_cockpit_backend.entity.Customer;

public interface CustomerService {

    Customer getById(int id);
    void save(Customer customer);

    Customer findByEmail(String email);
}
