package com.example.trabago.security.oauth2;

import com.example.trabago.model.User;
import com.example.trabago.security.CustomUserDetails;
import com.example.trabago.security.WebSecurityConfig;
import com.example.trabago.service.UserService;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

    public CustomOAuth2UserService(UserService userService, List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors) {
        this.userService = userService;
        this.oAuth2UserInfoExtractors = oAuth2UserInfoExtractors;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional = oAuth2UserInfoExtractors.stream()
                .filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.accepts(userRequest))
                .findFirst();
        if (oAuth2UserInfoExtractorOptional.isEmpty()) {
            throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet");
        }

        CustomUserDetails customUserDetails = oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
        User user = updateOrCreateUser(customUserDetails);
        customUserDetails.setId(user.getId());
        return customUserDetails;
    }

    private User updateOrCreateUser(CustomUserDetails user) {
        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());
        User existingUser;
        if (userOptional.isEmpty()) {
            existingUser = new User();
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            existingUser.setImageUrl(user.getAvatarUrl());
            existingUser.setProvider(OAuth2Provider.valueOf(String.valueOf(user.getProvider())));
            existingUser.setRole(WebSecurityConfig.USER);
        } else {
            existingUser = userOptional.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setImageUrl(user.getAvatarUrl());

        }
        return userService.saveUser(existingUser);
    }
}
