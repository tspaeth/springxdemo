package com.conserata.beer.init;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Company;
import com.conserata.beer.model.Country;
import com.conserata.beer.repository.BeerRepository;
import com.conserata.beer.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Service
class CompanyInitializer {

    @Autowired
    public CompanyInitializer(CompanyRepository companies) {




    }
}
