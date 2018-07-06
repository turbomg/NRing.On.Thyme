package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPackageItemRepository extends CrudRepository<BookingPackageItem, Long> {

}
