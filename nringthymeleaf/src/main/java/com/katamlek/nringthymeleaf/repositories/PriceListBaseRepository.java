package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Note;
import com.katamlek.nringthymeleaf.domain.PriceList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PriceListBaseRepository<T extends PriceList> extends CrudRepository<T, Long> {

}
