package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.CarPricing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarPricingRepository extends CrudRepository<CarPricing, Long> {

    public List<CarPricing> findByCarId(Long id);
}
