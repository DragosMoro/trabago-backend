package com.example.trabago.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobDTO
{
    private String company;
    private String position;
    private String location;
    private String description;
    private String column;
    private String date;
    private String salary;
    private String url;
    private String jobType;
    private String workMode;

    public JobDTO()
    {
    }
}
