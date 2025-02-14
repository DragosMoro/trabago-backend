package com.example.trabago.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "job_column")
public class JobColumn
{
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "column_order")
    private Integer order;

    @Column(name = "color")
    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "jobColumn", cascade = CascadeType.REMOVE)
    private List<Job> jobs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public JobColumn()
    {
    }

    public JobColumn(String name, Integer order, String color, User user)
    {
        this.name = name;
        this.order = order;
        this.color = color;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public JobColumn(String name, Integer order, String color)
    {
        this.name = name;
        this.order = order;
        this.color = color;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
