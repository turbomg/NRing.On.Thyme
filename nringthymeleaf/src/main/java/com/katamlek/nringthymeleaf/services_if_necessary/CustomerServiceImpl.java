package com.katamlek.nringthymeleaf.services_if_necessary;

import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Set<Customer> getCustomers() {
        Set<Customer> customerSet = new HashSet<>();
        customerRepository.findAll().iterator().forEachRemaining(customerSet::add);
        return customerSet;
    }
}
