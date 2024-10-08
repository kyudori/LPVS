/**
 * Copyright (c) 2023-2024, Samsung Electronics Co., Ltd. All rights reserved.
 *
 * Use of this source code is governed by a MIT license that can be
 * found in the LICENSE file.
 */
package com.lpvs.service;

import com.lpvs.repository.LPVSMemberRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OAuthServiceTest {

    @Test
    public void testLoadUser() throws OAuth2AuthenticationException {

        // Create a mock LPVSMemberRepository
        LPVSMemberRepository lpvsMemberRepository = mock(LPVSMemberRepository.class);

        // Create a sample OAuth2UserRequest
        ClientRegistration clientRegistration =
                ClientRegistration.withRegistrationId("google")
                        .userInfoUri("https://example.com/userinfo")
                        .userNameAttributeName("email")
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .clientId("id")
                        .tokenUri("https://example.com/tokenuri")
                        .redirectUri("https://example.com/redirecturi")
                        .authorizationUri("https://example.com/authorizationuri")
                        .build();
        OAuth2UserRequest userRequest =
                new OAuth2UserRequest(
                        clientRegistration,
                        new OAuth2AccessToken(
                                OAuth2AccessToken.TokenType.BEARER, "access-token", null, null));

        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("email", "testEmail");
        attributes.put("name", "testName");
        OAuth2User oAuth2User =
                new DefaultOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority("USER")),
                        attributes,
                        "email");

        // Mock the behavior of DefaultOAuth2UserService
        DefaultOAuth2UserService defaultUserService = Mockito.mock(DefaultOAuth2UserService.class);
        when(defaultUserService.loadUser(userRequest)).thenReturn(oAuth2User);

        // Create an instance of your OAuthService with the mocked DefaultOAuth2UserService
        OAuthService oAuthService = new OAuthService(lpvsMemberRepository);
        oAuthService.setOAuth2UserService(defaultUserService);

        OAuth2User loadedUser = oAuthService.loadUser(userRequest);

        assertEquals("testEmail", loadedUser.getAttribute("email"));
        assertEquals("testName", loadedUser.getAttribute("name"));
        assertEquals("google", loadedUser.getAttribute("provider"));
    }
}
