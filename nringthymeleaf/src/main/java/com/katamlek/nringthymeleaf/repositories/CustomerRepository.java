package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public List<Customer> findByBookings(List<Booking> bookings);
}
