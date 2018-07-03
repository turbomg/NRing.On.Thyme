package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPaymentRepository extends CrudRepository<BookingPayment, Long> {
}
