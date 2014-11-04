package com.conserata.beer.repository;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@RepositoryRestResource(path = "beer")
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {

    List<Beer> findByCompany(@Param("company") String companyName);
}
