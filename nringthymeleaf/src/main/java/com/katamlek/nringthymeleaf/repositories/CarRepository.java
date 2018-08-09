package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

}
