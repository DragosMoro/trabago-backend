package com.example.trabago.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JobColumnDTO {


    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;

    public JobColumnDTO(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public JobColumnDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
