package com.example.DiningReview.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String address;
    private String zipCode;
    private String phone;
    private Double peanutRating;
    private Double eggRating;
    private Double diaryRating;
    private Double overallRating;



}
