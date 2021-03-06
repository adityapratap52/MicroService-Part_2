package com.moviecatalog.service;

import com.moviecatalog.models.Rating;
import com.moviecatalog.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {

        return restTemplate.getForObject("http://ratings-data-service/rating/user/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRating(String userId) {

        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatingList(
                Arrays.asList(
                        new Rating("0", 0)
                ));

        return userRating;
    }
}
