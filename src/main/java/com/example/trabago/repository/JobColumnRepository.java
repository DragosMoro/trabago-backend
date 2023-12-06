package com.example.trabago.repository;

import com.example.trabago.model.JobColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobColumnRepository extends JpaRepository<JobColumn, UUID> {

}
