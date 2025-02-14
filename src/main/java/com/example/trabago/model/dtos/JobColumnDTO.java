package com.example.trabago.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class JobColumnDTO
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;

    @JsonProperty("userId")
    private UUID userId;

    public JobColumnDTO(String name, String color, UUID userId)
    {
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    public JobColumnDTO()
    {
    }
}
