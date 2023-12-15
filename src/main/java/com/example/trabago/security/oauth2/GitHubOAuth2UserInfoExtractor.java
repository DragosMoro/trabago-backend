package com.example.trabago.security.oauth2;

import com.example.trabago.security.CustomUserDetails;

import com.example.trabago.security.WebSecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GitHubOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    @Override
    public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
        //spilt the name into first and last name
        String name = retrieveAttr("name", oAuth2User);
        String[] nameSplit = name.split(" ");
        String firstName = nameSplit[0];
        String lastName = nameSplit[1];

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setFirstName(firstName);
        customUserDetails.setLastName(lastName);
        customUserDetails.setEmail(retrieveAttr("email", oAuth2User));
        customUserDetails.setAvatarUrl(retrieveAttr("avatar_url", oAuth2User));
        customUserDetails.setProvider(OAuth2Provider.GITHUB);
        customUserDetails.setAttributes(oAuth2User.getAttributes());
        customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(WebSecurityConfig.USER)));
        return customUserDetails;
    }

    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return OAuth2Provider.GITHUB.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    private String retrieveAttr(String attr, OAuth2User oAuth2User) {
        Object attribute = oAuth2User.getAttributes().get(attr);
        return attribute == null ? "" : attribute.toString();
    }
}
