package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.BookingPackageItemCar;
import com.katamlek.nringthymeleaf.domain.BookingPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingPaymentRepository extends CrudRepository<BookingPayment, Long> {

    public List<BookingPayment> findByBooking(Booking booking);

}
