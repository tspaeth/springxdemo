package com.conserata.beer.web;

import com.conserata.beer.model.Beer;
import com.conserata.beer.model.Rating;
import com.conserata.beer.repository.BeerRepository;
import com.conserata.beer.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * This Spring MVC controller just shows how to use MVC controllers being exposed as REST-endpoints
 * The demo service in this class just calculates the average rating of one beer by selecting all
 * ratings from the database and then just iterating through the resultset
 */
@Controller
public class CustomRatingController {

    // inject the rating repository for searching through the Ratings-entity
    @Autowired
    private RatingRepository ratingRepository;

    @RequestMapping("/beer/{beerId}/ratingsum")
    @ResponseBody
    Float getAverageRating(@PathVariable("beerId") Long beerId) {
        List<Rating> allRatingsForOneBeer = (List<Rating>)ratingRepository.findAllByBeerId(beerId);
        int sum = 0;
        int cnt = 0;
        for (Rating rating : allRatingsForOneBeer) {
            cnt++;
            sum = sum + rating.getScore();
        }

        Float average = (new Float(sum).floatValue() / new Float(cnt).floatValue());
        return average;
    }

}
