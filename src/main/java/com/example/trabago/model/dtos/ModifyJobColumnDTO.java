package com.example.trabago.model.dtos;

import java.util.UUID;

public class ModifyJobColumnDTO {
    private String name;
    private String color;
    private Integer order;

    private UUID id;

    public ModifyJobColumnDTO(String name, String color, Integer order, UUID id) {
        this.name = name;
        this.color = color;
        this.order = order;
        this.id = id;
    }

    public ModifyJobColumnDTO() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getOrder() {
        return order;
    }

    public UUID getId() {
        return id;
    }
}
