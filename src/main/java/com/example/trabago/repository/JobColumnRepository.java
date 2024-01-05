package com.example.trabago.repository;

import com.example.trabago.model.JobColumn;
import com.example.trabago.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobColumnRepository extends JpaRepository<JobColumn, UUID> {

    JobColumn findByName(String name);
    List<JobColumn> getJobColumnsByUser(User user);

}
