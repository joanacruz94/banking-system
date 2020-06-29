package com.ironhack.server.service;

import com.ironhack.server.dto.AdminSummary;
import com.ironhack.server.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public AdminSummary getCurrentUser(UserPrincipal userPrincipal) {
        return AdminSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .name(userPrincipal.getName())
                .build();
    }
}
