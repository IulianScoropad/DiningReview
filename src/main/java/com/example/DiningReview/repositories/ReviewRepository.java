package com.example.DiningReview.repositories;


import com.example.DiningReview.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

}
