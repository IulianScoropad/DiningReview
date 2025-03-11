package com.example.DiningReview.controllers;


import com.example.DiningReview.models.RestaurantUser;
import com.example.DiningReview.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RestaurantUser restaurantUser) {
        Optional<RestaurantUser> userOptional =  userRepository.findRestaurantUserByName(restaurantUser.getName());
        if (userOptional.isPresent()) {
            return new ResponseEntity<>("Restaurant User Already Exists", HttpStatus.CONFLICT);
        }
        userRepository.save(restaurantUser);
        return new ResponseEntity<>("Restaurant User Added", HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<RestaurantUser> fetchUserByName(@PathVariable String userName) {
        Optional<RestaurantUser> userOptional = userRepository.findRestaurantUserByName(userName);
        return userOptional.map(
                restaurantUser -> new ResponseEntity<>(restaurantUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{userName}")
    public ResponseEntity<RestaurantUser> updateUser(@PathVariable String userName, @RequestBody RestaurantUser restaurantUser) {
        Optional<RestaurantUser> userOptional = userRepository.findRestaurantUserByName(userName);
        return userOptional.map(
                changeUser -> {
                    if (restaurantUser.getCity() != null) changeUser.setCity(restaurantUser.getCity());
                    if (restaurantUser.getState() != null) changeUser.setState(restaurantUser.getState());
                    if (restaurantUser.getZipcode() != null) changeUser.setZipcode(restaurantUser.getZipcode());
                    if (restaurantUser.getDairyAllergen() != null) changeUser.setDairyAllergen(restaurantUser.getDairyAllergen());
                    if (restaurantUser.getEggAllergen() != null) changeUser.setEggAllergen(restaurantUser.getEggAllergen());
                    if(restaurantUser.getPeanutAllergen() != null) changeUser.setPeanutAllergen(restaurantUser.getPeanutAllergen());

                    userRepository.save(changeUser);
                    return new ResponseEntity<>(changeUser, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}