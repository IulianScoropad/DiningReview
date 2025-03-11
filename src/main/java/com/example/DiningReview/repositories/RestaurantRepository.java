package com.example.DiningReview.repositories;

import com.example.DiningReview.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
