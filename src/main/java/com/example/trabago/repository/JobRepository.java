package com.example.trabago.repository;

import com.example.trabago.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository  extends JpaRepository<Job, UUID> {
    void deleteById(UUID id);

    List<Job> findByJobColumnId(UUID id);


}
