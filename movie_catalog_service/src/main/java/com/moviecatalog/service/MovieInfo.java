package com.moviecatalog.service;

import com.moviecatalog.models.CatalogItem;
import com.moviecatalog.models.Movie;
import com.moviecatalog.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {

        Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }


    private CatalogItem getFallbackCatalogItem(Rating rating) {

        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
