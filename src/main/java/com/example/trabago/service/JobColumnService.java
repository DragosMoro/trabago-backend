package com.example.trabago.service;

import com.example.trabago.model.Job;
import com.example.trabago.model.JobColumn;
import com.example.trabago.model.JobColumnDTO;
import com.example.trabago.model.JobDTO;
import com.example.trabago.repository.JobColumnRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class JobColumnService {
    private final JobColumnRepository jobColumnRepository;

    public JobColumnService(JobColumnRepository jobColumnRepository) {
        this.jobColumnRepository = jobColumnRepository;
    }


    public void saveColumn (JobColumn jobColumn) {
        jobColumn.setOrder(jobColumnRepository.findAll().size() + 1);
        jobColumnRepository.save(jobColumn);
    }

    public void deleteColumn(UUID id) {
        jobColumnRepository.deleteById(id);
    }
    public List<JobColumn> getAllColumns() {
        return jobColumnRepository.findAll();
    }

    public JobColumn getJobColumnById(UUID id) {
        return jobColumnRepository.findById(id).orElseThrow();
    }


    public void updateColumn(JobColumn jobColumn) {
        jobColumnRepository.save(jobColumn);
    }

    public JobColumn getJobColumnByName(String name) {
        return jobColumnRepository.findByName(name);
    }
}
