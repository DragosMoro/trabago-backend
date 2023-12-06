package com.example.trabago.model;

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

    @Column(name="description")
    private String description;

    @Column(name="image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "job_column_id", referencedColumnName = "id", nullable=false)
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

    public Job(String company, String position, String location, String date, Integer order, String description, String imageUrl, JobColumn jobColumn) {
        this.company = company;
        this.position = position;
        this.location = location;
        this.date = date;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.order = order;
        this.description = description;
        this.imageUrl = imageUrl;
        this.jobColumn = jobColumn;
    }

    public UUID getId() {
        return id;
    }
}
