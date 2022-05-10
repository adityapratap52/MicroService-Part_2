package com.ratingdata.resources;

import com.ratingdata.models.Rating;
import com.ratingdata.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class RatingDataResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {

        return new Rating(movieId, 4);
    }


    @GetMapping("/user/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {

//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 8),
//                new Rating("5678", 9)
//        );

        UserRating userRating = new UserRating();
//        userRating.setRatingList(ratings);

        userRating.initData(userId);

        return userRating;
    }
}
