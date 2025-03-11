package com.example.DiningReview.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long restaurantId;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer diaryScore;
    private String comentary;
    private Status status;
}
