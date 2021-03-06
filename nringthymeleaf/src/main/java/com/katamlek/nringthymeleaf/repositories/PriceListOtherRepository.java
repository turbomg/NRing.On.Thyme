package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.PriceListOther;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListOtherRepository extends CrudRepository<PriceListOther, Long> {
}
