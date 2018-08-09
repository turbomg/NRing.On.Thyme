package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingPackageItemRepository extends CrudRepository<BookingPackageItem, Long> {

    public List<BookingPackageItem> findDistinctByBooking(Booking booking);
}
