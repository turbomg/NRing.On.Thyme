package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.CalendarEntry;
import com.katamlek.nringthymeleaf.domain.TemporaryPackageItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryPackageItemRepository extends CrudRepository<TemporaryPackageItem, Long> {
    public List<TemporaryPackageItem> findByBooking(Booking booking);

    // public List<TemporaryPackageItem> findEventRelatedItemsOnly() {}

//    @Query(value = "SELECT item_date, event_name, sum(statistisc_count) FROM temporary_package_item tpi, event e\n" + "WHERE tpi.event_id = e.id GROUP BY item_date, event_name", nativeQuery = true)
//    public void see();
}