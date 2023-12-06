package com.example.trabago.controller;

import com.example.trabago.model.Test;
import com.example.trabago.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {
    @Autowired
    private JobRepository testRepository;

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody String name) {
        Test test = new Test(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
