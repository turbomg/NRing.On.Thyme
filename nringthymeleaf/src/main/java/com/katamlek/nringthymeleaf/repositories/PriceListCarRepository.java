package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.domain.PriceListCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListCarRepository extends CrudRepository<PriceListCar, Long> {

    public List<PriceListCar> findDistinctByCar(Car car);
}
