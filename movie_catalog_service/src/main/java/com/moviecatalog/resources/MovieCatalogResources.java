package com.moviecatalog.resources;

import com.moviecatalog.models.CatalogItem;
import com.moviecatalog.models.UserRating;
import com.moviecatalog.service.MovieInfo;
import com.moviecatalog.service.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

    @GetMapping("/{userId}")
    // This is not a good way bcz if any microservice is not work then hole application throw fault tolerance
//    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = userRatingInfo.getUserRating(userId);

        // we can use WebClient.Builder instead of RestTemplate
            /*
            Movie movie = webClientBuilder.build()
                    .get()               // for get request otherwise post()
                    .uri("http://localhost:8082/movie/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
             */

        return ratings.getRatingList().stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());


//        return Collections.singletonList(
//          new CatalogItem("Transformers", "Test", 4)
//        );
    }


//    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
//
//        return Arrays.asList(new CatalogItem("No Movie", "", 0));
//    }
}
