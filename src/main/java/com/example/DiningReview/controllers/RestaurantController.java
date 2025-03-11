package com.example.DiningReview.controllers;

import com.example.DiningReview.models.Restaurant;
import com.example.DiningReview.repositories.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (optionalRestaurant.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        restaurantRepository.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> fetchRestaurantById(@PathVariable Long id) {

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant
                .map(restaurant -> new ResponseEntity<>(restaurant, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Restaurant>> searchRestaurantsByZipCodeAndPossibleAllergen(@RequestParam String zipCode, @RequestParam(required = false) String possibleAllergen) {

        Iterable<Restaurant> restaurants;

        if (possibleAllergen.equalsIgnoreCase("peanut")) {
            restaurants = restaurantRepository.findRestaurantByZipCodeAndPeanutRatingNotNull(zipCode);
        } else if (possibleAllergen.equalsIgnoreCase("egg")) {
            restaurants = restaurantRepository.findRestaurantByZipCodeAndEggRatingNotNull(zipCode);
        } else if (possibleAllergen.equalsIgnoreCase("diary")) {
            restaurants = restaurantRepository.findRestaurantByZipCodeAndDiaryRatingNotNull(zipCode);
        }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

}
