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
 */
@RepositoryRestResource(path="rating")
public interface RatingRepository extends PagingAndSortingRepository<Rating, Long> {
    List<Rating> findAllByBeerId(Long beerId);
}
