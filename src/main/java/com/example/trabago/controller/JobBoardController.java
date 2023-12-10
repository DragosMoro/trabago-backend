package com.example.trabago.controller;

import com.example.trabago.model.JobColumn;
import com.example.trabago.model.JobColumnDTO;
import com.example.trabago.service.JobColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/board/")
public class JobBoardController {
    @Autowired
    private JobColumnService jobColumnService;


}
