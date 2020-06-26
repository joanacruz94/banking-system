package com.ironhack.bankSystem.service;

import com.ironhack.bankSystem.dto.UserSummary;
import com.ironhack.bankSystem.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .name(userPrincipal.getName())
                .build();
    }
}
