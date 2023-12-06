package com.example.trabago.controller;

import com.example.trabago.model.Job;
import com.example.trabago.model.JobColumn;
import com.example.trabago.model.Test;
import com.example.trabago.repository.JobRepository;
import com.example.trabago.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/jobs")
public class JobController {
    // create the CRUD methods
    @Autowired
   private JobService jobService;
    // create a method to get all jobs
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllJobs() {
        try {
            return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the list of jobs: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create a method to get all jobs by column id
    @GetMapping(value = "/getAll/{id}")
    public ResponseEntity<?> getJobsFromColumn(@PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(jobService.getJobsByColumnId(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the list of jobs: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // create a method to create a job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        try {
            Job job1 = new Job(job.getCompany(), job.getPosition(), job.getLocation(), job.getDate(), job.getOrder(), job.getDescription(), job.getImageUrl(), job.getJobColumn());
            jobService.saveJob(job1);
            return new ResponseEntity<>(job1, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody List<Job> jobs) {
        try {
            for (Job job : jobs) {
                jobService.updateJob(job);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while updating the job: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
