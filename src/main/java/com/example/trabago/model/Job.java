package com.example.trabago.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job
{
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

    @Column(name = "description", columnDefinition = "TEXT")
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


    public Job()
    {
    }

    public Job(String company, String position, String location, String date, Integer order, String description, String imageUrl, String salary, String jobType, String jobUrl, String workMode, JobColumn jobColumn)
    {
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
}
