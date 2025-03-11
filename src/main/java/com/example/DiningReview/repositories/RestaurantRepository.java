package com.example.DiningReview.repositories;

import com.example.DiningReview.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantByNameAndZipCode(String restaurantName, String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndPeanutRatingNotNull(String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndEggRatingNotNull(String zipCode);
    List<Restaurant> findRestaurantByZipCodeAndDiaryRatingNotNull(String zipCode);

}
