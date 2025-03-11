package com.example.DiningReview.repositories;

import com.example.DiningReview.models.RestaurantUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<RestaurantUser, Long> {

    Optional<RestaurantUser> findRestaurantUserByName(String restaurantUserName);
}
