package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.PriceList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListEventRepository extends PriceListBaseRepository<PriceList> {
  //  public List<String> findDistinctByEventId(); //todo is it ok?
}
