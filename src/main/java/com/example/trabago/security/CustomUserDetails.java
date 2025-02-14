package com.example.trabago.security;

import com.example.trabago.security.oauth2.OAuth2Provider;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Data
public class CustomUserDetails implements OAuth2User, UserDetails
{
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private OAuth2Provider provider;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String getName()
    {
        return firstName + " " + lastName;
    }

    @Override
    public <A> A getAttribute(String name)
    {
        return OAuth2User.super.getAttribute(name);
    }
}
