package com.conserata.beer.repository;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Rating;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * Gives access to the JPA Rating-Table and also maps the REST endpoint on /rating
 */
@RepositoryRestResource(path="rating")
public interface RatingRepository extends PagingAndSortingRepository<Rating, Long> {

    // custom search method on ratings by beerId
    List<Rating> findAllByBeerId(Long beerId);
}
