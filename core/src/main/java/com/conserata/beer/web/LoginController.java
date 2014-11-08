package com.conserata.beer.web;

import com.conserata.beer.model.Rating;
import com.conserata.beer.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * (c) conserata IT-Consulting
 * @author tspaeth
 *
 * This is just an example base for extending it with a different approach
 * -- currently not used --
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    @ResponseBody
    Boolean login() {
        System.out.println("Login called");
        return false;
    }

}
