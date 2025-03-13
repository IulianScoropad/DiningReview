package com.example.DiningReview.controllers;

import com.example.DiningReview.models.AdminReview;
import com.example.DiningReview.models.Restaurant;
import com.example.DiningReview.models.Review;
import com.example.DiningReview.models.Status;
import com.example.DiningReview.repositories.RestaurantRepository;
import com.example.DiningReview.repositories.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminReviewController {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;


    public AdminReviewController(RestaurantRepository restaurantRepository,
                                 ReviewRepository reviewRepository) {

        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;

    }


    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findAllReviewsByStatus(@RequestParam Status status) {
        Status currentStatus;
        try{
            currentStatus = Status.valueOf(status.name().toUpperCase());
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(reviewRepository.findReviewByStatus(currentStatus), HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReviewStatus(@PathVariable Long reviewId, @RequestBody AdminReview adminReview) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (optionalReview.isPresent()) {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(optionalReview.get().getRestaurantId());
            if (optionalRestaurant.isPresent()) {
                Review review = optionalReview.get();
                if(adminReview.getAccepted()){
                    review.setStatus(Status.APPROVED);
                }else{
                    review.setStatus(Status.REJECTED);
                }
                reviewRepository.save(review);
                updateRestaurantScore(optionalRestaurant.get());
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void updateRestaurantScore(Restaurant restaurant) {

        List<Review> reviews = reviewRepository.findReviewByStatusAndRestaurantId(Status.APPROVED, restaurant.getId());

        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        int eggCount = 0;
        int diaryCount = 0;
        int peanutCount = 0;

        int eggSum = 0;
        int diarySum = 0;
        int peanutSum = 0;

        for (Review review : reviews) {

            if (review.getPeanutScore() != null) {
                peanutSum += review.getPeanutScore();
                peanutCount++;
            }
            if (review.getDiaryScore() != null) {
                diarySum += review.getDiaryScore();
                diaryCount++;
            }
            if (review.getEggScore() != null) {
                eggSum += review.getEggScore();
                eggCount++;
            }

        }

        double totalScore = (double) (eggSum + diarySum + peanutSum) /(peanutCount + diaryCount + eggCount);
        restaurant.setOverallRating(totalScore);

        if(peanutCount > 0){
            double peanutScore = (double) peanutSum / peanutCount;
            restaurant.setPeanutRating(peanutScore);
        }
        if(diaryCount > 0){
            double diaryScore = (double) diarySum / diaryCount;
            restaurant.setDiaryRating(diaryScore);
        }
        if(eggCount > 0){
            double eggScore = (double) eggSum / eggCount;
            restaurant.setEggRating(eggScore);
        }

        restaurantRepository.save(restaurant);
    }





}
