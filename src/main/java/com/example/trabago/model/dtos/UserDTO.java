package com.example.trabago.model.dtos;

import java.util.UUID;

public record UserDTO (UUID id, String firstName, String lastName, String email, String role) {
}
