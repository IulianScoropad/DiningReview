package com.example.DiningReview.controllers;

import com.example.DiningReview.models.Restaurant;
import com.example.DiningReview.models.RestaurantUser;
import com.example.DiningReview.models.Review;
import com.example.DiningReview.models.Status;
import com.example.DiningReview.repositories.RestaurantRepository;
import com.example.DiningReview.repositories.ReviewRepository;
import com.example.DiningReview.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            UserRepository userRepository,
                            RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {

        Optional<RestaurantUser> optionalUser = userRepository.findRestaurantUserByName(review.getName());
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if (optionalUser.isPresent() && optionalRestaurant.isPresent()) {
            if(review.getDiaryScore() == null && review.getEggScore() == null && review.getPeanutScore() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            review.setStatus(Status.UNCHECKED);
            reviewRepository.save(review);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
