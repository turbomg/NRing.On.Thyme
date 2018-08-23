package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.TemporaryPackageItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryPackageItemRepository extends CrudRepository<TemporaryPackageItem, Long> {
    public List<TemporaryPackageItem> findByBooking(Booking booking);

    // public List<TemporaryPackageItem> findEventRelatedItemsOnly() {}
}