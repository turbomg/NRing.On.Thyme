package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingCarRepository extends CrudRepository<BookingCar, Long> {

    public BookingCar findBookingCarByBookingId(Long bookingId);
}
