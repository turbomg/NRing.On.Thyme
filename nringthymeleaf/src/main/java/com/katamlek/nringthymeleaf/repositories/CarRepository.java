package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
