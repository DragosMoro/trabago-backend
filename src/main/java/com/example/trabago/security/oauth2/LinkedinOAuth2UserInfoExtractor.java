package com.example.trabago.security.oauth2;

import com.example.trabago.security.CustomUserDetails;
import com.example.trabago.security.WebSecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class LinkedinOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {
    @Override
    public CustomUserDetails extractUserInfo(OAuth2User oAuth2User)  {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setEmail("");
        customUserDetails.setFirstName("");
        customUserDetails.setLastName("");
        customUserDetails.setAvatarUrl("");
        customUserDetails.setProvider(OAuth2Provider.LINKEDIN);
        customUserDetails.setAttributes(oAuth2User.getAttributes());
        customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(WebSecurityConfig.USER)));
        return customUserDetails;
    }

    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return OAuth2Provider.LINKEDIN.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }
    private String getAttributeValue(String attribute, OAuth2User oAuth2User) {
        Object attributeValue = oAuth2User.getAttributes().get(attribute);
        return attributeValue == null ? "" : attributeValue.toString();
    }
}
