package com.example.trabago.mapper;

import com.example.trabago.model.User;
import com.example.trabago.model.dtos.UserDTO;

public interface UserMapper
{
    UserDTO toUserDto(User user);
}