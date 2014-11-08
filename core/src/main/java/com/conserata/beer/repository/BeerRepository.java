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
 *
 * Gives access to the JPA Beer-Table and also maps the REST endpoint on /beer
 */
@RepositoryRestResource(path = "beer")
public interface BeerRepository extends PagingAndSortingRepository<Beer, Long> {

    // custom search method
    List<Beer> findByCompany(@Param("company") String companyName);
}
