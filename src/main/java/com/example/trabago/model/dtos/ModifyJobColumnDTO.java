package com.example.trabago.model.dtos;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ModifyJobColumnDTO
{
    private String name;
    private String color;
    private Integer order;
    private UUID id;

    public ModifyJobColumnDTO(String name, String color, Integer order, UUID id)
    {
        this.name = name;
        this.color = color;
        this.order = order;
        this.id = id;
    }

    public ModifyJobColumnDTO()
    {
    }
}
