package com.example.trabago.controller;

import com.example.trabago.mapper.UserMapper;
import com.example.trabago.model.User;
import com.example.trabago.model.dtos.UserDTO;
import com.example.trabago.security.CustomUserDetails;
import com.example.trabago.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.trabago.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController
{

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/me")
    public UserDTO getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser)
    {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());
        return userMapper.toUserDto(user);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<UserDTO> getUsers()
    {
        return userService.getUsers().stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{email}")
    public UserDTO getUser(@PathVariable String email)
    {
        return userMapper.toUserDto(userService.validateAndGetUserByEmail(email));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{email}")
    public UserDTO deleteUser(@PathVariable String email)
    {
        User user = userService.validateAndGetUserByEmail(email);
        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }
}
