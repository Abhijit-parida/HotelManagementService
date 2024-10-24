package com.hsm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// only access by owner and admin

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    @PostMapping("/add-country")
    public String addCountry() {
        return "added";
    }
}
