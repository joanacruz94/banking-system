package com.ironhack.server.service;

import com.ironhack.server.dto.UserSummary;
import com.ironhack.server.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .name(userPrincipal.getName())
                .roles(userPrincipal.getRoles())
                .build();
    }
}
