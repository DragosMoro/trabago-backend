package com.example.trabago.mapper;

import com.example.trabago.model.User;
import com.example.trabago.model.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper{
    @Override
    public UserDTO toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }
}
