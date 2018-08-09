package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import com.katamlek.nringthymeleaf.domain.BookingPackageItemCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingPackageItemCarRepository extends CrudRepository<BookingPackageItemCar, Long> {

    public List<BookingPackageItemCar> findByDate(Date date);

}
