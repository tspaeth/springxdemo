package com.conserata.beer.init;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Company;
import com.conserata.beer.model.Country;
import com.conserata.beer.model.Rating;
import com.conserata.beer.repository.BeerRepository;
import com.conserata.beer.repository.CompanyRepository;
import com.conserata.beer.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 */
@Service
class BeerInitializer {

    @Autowired
    public BeerInitializer(BeerRepository beers, CompanyRepository companies, RatingRepository ratings) {
        Assert.notNull(beers, "BeerRepository must not be null!");
        Assert.notNull(companies, "CompanyRepository must not be null!");

        beers.deleteAll();
        companies.deleteAll();
        ratings.deleteAll();

        Company company = new Company();
        company.setCompanyName("Beck's Breweries");
        company.setCompanyAddress("Bremen");
        company.setCountry(Country.DE);

        companies.save(company);

        Company warsteiner = new Company();
        warsteiner.setCompanyName("Warsteiner");
        warsteiner.setCompanyAddress("Warstein");
        warsteiner.setCountry(Country.DE);

        companies.save(warsteiner);


        Beer beer = new Beer();
        beer.setBrand("Becks");
        beer.setAlcVol(new Float(5.1));
        beer.setComment("Bitter, but sweet and wild");
        beer.setCompany(company);
        beer.setCompanyLogoURL("");

        beers.save(beer);
        Rating ratingBecks = new Rating();
        ratingBecks.setBeer(beer);
        ratingBecks.setScore(5);
        ratings.save(ratingBecks);
        ratingBecks = new Rating();
        ratingBecks.setBeer(beer);
        ratingBecks.setScore(8);
        ratings.save(ratingBecks);

        beer = new Beer();
        beer.setBrand("Warsteiner");
        beer.setAlcVol(new Float(4.8));
        beer.setComment("Sweet");
        beer.setCompany(warsteiner);
        beer.setCompanyLogoURL("http://upload.wikimedia.org/wikipedia/de/thumb/0/0e/Rundlogo_Warsteiner_Brauerei.jpg/240px-Rundlogo_Warsteiner_Brauerei.jpg");
        beers.save(beer);


    }
}
