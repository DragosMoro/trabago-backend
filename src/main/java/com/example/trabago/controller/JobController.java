package com.example.trabago.controller;

import com.example.trabago.model.Job;
import com.example.trabago.model.JobColumn;
import com.example.trabago.model.dtos.JobDTO;
import com.example.trabago.service.JobColumnService;
import com.example.trabago.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/jobs")
public class JobController {
    // create the CRUD methods
    @Autowired
    private JobService jobService;
    // create a method to get all jobs
    @Autowired
    private JobColumnService jobColumnService;
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
    public ResponseEntity<Job> createJobFromModal(@RequestBody JobDTO jobDTO) {
        try {
            JobColumn jobColumn = jobColumnService.getJobColumnByName(jobDTO.getColumn());
            int order = jobService.getJobsByColumnId(jobColumn.getId()).size() + 1;
            String jobType = jobDTO.getJobType().equals("Choose Job Type") ? "" : jobDTO.getJobType();
            String workMode = jobDTO.getWorkMode().equals("Choose Work Mode") ? "" : jobDTO.getWorkMode();
            Job job1 = new Job(jobDTO.getCompany(), jobDTO.getPosition(), jobDTO.getLocation(), jobDTO.getDate(), order, jobDTO.getDescription(), "", jobDTO.getSalary(), jobType, jobDTO.getUrl(), workMode, jobColumn);

            jobService.saveJob(job1);
            return new ResponseEntity<>(job1, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody List<Job> jobs) {
        try {
            for (int i = 0; i < jobs.size(); i++) {
                jobs.get(i).setOrder(i + 1);
                jobService.updateJob(jobs.get(i));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while updating the job: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
