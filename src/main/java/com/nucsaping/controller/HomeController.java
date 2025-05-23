package com.nucsaping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Test successful", HttpStatus.OK);
    }
}
