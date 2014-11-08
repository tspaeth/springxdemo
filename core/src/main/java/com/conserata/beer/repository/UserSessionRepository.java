package com.conserata.beer.repository;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.UserSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * Gives access to the JPA UserSession-Table  - no REST mapping to the outside
 */
@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, Long> {

    // for looking up a user session object by token
    UserSession findOneUserSessionBySessionToken(String sessionToken);

}
