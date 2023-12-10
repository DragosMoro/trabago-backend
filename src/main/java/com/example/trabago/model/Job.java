package com.example.trabago.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "company")
    private String company;
    @Column(name = "position")
    private String position;
    @Column(name = "location")
    private String location;
    @Column(name = "date")
    private String date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "column_order")
    private Integer order;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "salary")
    private String salary;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "job_url")
    private String jobUrl;

    @Column(name = "work_mode")
    private String workMode;


    @ManyToOne
    @JoinColumn(name = "job_column_id", referencedColumnName = "id", nullable = false)
    private JobColumn jobColumn;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public JobColumn getJobColumn() {
        return jobColumn;
    }

    public void setJobColumn(JobColumn jobColumn) {
        this.jobColumn = jobColumn;
    }

    public Job() {
    }

    public Job(String company, String position, String location, String date, Integer order, String description, String imageUrl, String salary, String jobType, String jobUrl, String workMode, JobColumn jobColumn) {
        this.company = company;
        this.position = position;
        this.location = location;
        this.date = date;
        this.order = order;
        this.description = description;
        this.imageUrl = imageUrl;
        this.salary = salary;
        this.jobType = jobType;
        this.jobUrl = jobUrl;
        this.workMode = workMode;
        this.jobColumn = jobColumn;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }
}
