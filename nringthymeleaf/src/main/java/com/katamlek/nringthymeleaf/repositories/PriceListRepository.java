package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.PriceList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends PriceListBaseRepository<PriceList> {
}
