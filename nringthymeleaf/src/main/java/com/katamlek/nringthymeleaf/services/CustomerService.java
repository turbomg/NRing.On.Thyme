package com.katamlek.nringthymeleaf.services;

import com.katamlek.nringthymeleaf.domain.Customer;

import java.util.Set;

public interface CustomerService {
    Set<Customer> getCustomers();
}
