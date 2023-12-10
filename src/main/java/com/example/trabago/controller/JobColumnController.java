package com.example.trabago.controller;

import com.example.trabago.model.JobColumn;

import com.example.trabago.model.JobColumnDTO;
import com.example.trabago.service.JobColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/jobColumn")
public class JobColumnController {
    @Autowired
    private JobColumnService jobColumnService;

    @PostMapping
    public ResponseEntity<JobColumn> createJobColumnFromModal(@RequestBody JobColumnDTO jobColumn) {
        try {
            int order = jobColumnService.getAllColumns().size() + 1;
            JobColumn jobColumn1 = new JobColumn(jobColumn.getName(), order, jobColumn.getColor());
            jobColumnService.saveColumn(jobColumn1);
            return new ResponseEntity<>(jobColumn1, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllJobColumns() {
        try {
            List<JobColumn> jobColumns = jobColumnService.getAllColumns();
            return new ResponseEntity<>(jobColumns, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the list of job columns: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all the jobs from a specific column
    @GetMapping(value = "/getJobs/{id}")
    public ResponseEntity<?> getJobsFromColumn(@PathVariable("id") UUID id) {
        try {
            JobColumn jobColumn = jobColumnService.getJobColumnById(id);
            return new ResponseEntity<>(jobColumn.getJobs(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the list of jobs: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody List<JobColumn> jobColumns) {
        try {
            for (JobColumn jobColumn : jobColumns) {
                jobColumnService.updateColumn(jobColumn);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while updating the order of the columns: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
