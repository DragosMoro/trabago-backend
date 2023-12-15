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

@RestController
@RequestMapping("api/jobColumn")
public class JobColumnController {
    @Autowired
    private JobColumnService jobColumnService;

    @PostMapping
    public ResponseEntity<?> createJobColumnFromModal(@RequestBody JobColumnDTO jobColumn) {
        try {
            int order = jobColumnService.getAllColumns().size() + 1;
            if( jobColumnService.getJobColumnByName(jobColumn.getName()) != null)
                throw new IllegalArgumentException("Column name already exists. Please choose a different name.");

            JobColumn jobColumn1 = new JobColumn(jobColumn.getName(), order, jobColumn.getColor());
            jobColumnService.saveColumn(jobColumn1);
            return new ResponseEntity<>(jobColumn1, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity<?> updateJobColumn(@RequestBody JobColumn jobColumn) {
        try {
            JobColumn existingColumn = jobColumnService.getJobColumnByName(jobColumn.getName());
            if(existingColumn != null && !existingColumn.getId().equals(jobColumn.getId())) {
                throw new IllegalArgumentException("Column name already exists. Please choose a different name.");
            }
            jobColumnService.updateColumn(jobColumn);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteJobColumn(@PathVariable("id") UUID id) {
        try {
            jobColumnService.deleteColumn(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while deleting the column: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
