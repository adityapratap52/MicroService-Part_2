package com.ratingdata.models;

import java.util.Arrays;
import java.util.List;

public class UserRating {

    private String userId;
    private List<Rating> ratingList;

    public UserRating() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public void initData(String userId) {
        this.setUserId(userId);
        this.setRatingList(Arrays.asList(
                new Rating("100", 3),
                new Rating("200", 4)
        ));
    }
}
