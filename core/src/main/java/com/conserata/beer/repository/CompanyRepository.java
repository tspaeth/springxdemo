package com.conserata.beer.repository;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * Gives access to the JPA Company-Table and also maps the REST endpoint on /company
 */
@RepositoryRestResource(path = "company")
// if we would have roles....
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

}
