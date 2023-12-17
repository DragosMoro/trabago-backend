package com.example.trabago.service;

import com.example.trabago.model.Job;
import com.example.trabago.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void saveJob(Job job) {

        jobRepository.save(job);
    }

    public void deleteJob(UUID id) {
        jobRepository.deleteById(id);
    }

    public Job getJobById(UUID id) {
        return jobRepository.findById(id).orElseThrow();
    }

    public void updateJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public List<Job> getJobsByColumnId(UUID id) {
        return jobRepository.findByJobColumnId(id);
    }


}
