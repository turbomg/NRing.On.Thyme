package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.PriceListEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListEventRepository extends CrudRepository<PriceListEvent, Long> {
  //  public List<String> findDistinctByEventId(); //todo is it ok?
}
