package com.moviecatalog.resources;

import com.moviecatalog.models.CatalogItem;
import com.moviecatalog.models.Movie;
import com.moviecatalog.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // fake rating data
//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 4),
//                new Rating("5678", 3)
//        );

        // We can use UserRating data instead of fake data
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/rating/user/" + userId, UserRating.class);

        return ratings.getRatingList().stream().map(rating -> {

            // for each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(), Movie.class);


            // we can use WebClient.Builder instead of RestTemplate
            /*
            Movie movie = webClientBuilder.build()
                    .get()               // for get request otherwise post()
                    .uri("http://localhost:8082/movie/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
             */

            // put them all together
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());


//        return Collections.singletonList(
//          new CatalogItem("Transformers", "Test", 4)
//        );
    }
}
