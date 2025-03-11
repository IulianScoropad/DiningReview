package com.example.DiningReview.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String state;
    private String zipcode;
    private Boolean peanutAllergen;
    private Boolean eggAllergen;
    private Boolean dairyAllergen;
}
