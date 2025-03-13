package com.example.DiningReview.repositories;


import com.example.DiningReview.models.Review;
import com.example.DiningReview.models.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findReviewByStatusAndRestaurantId(Status status, Long restaurantId);
    List<Review> findReviewByStatus(Status status);

}
