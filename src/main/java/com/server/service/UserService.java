package com.server.service;

import com.server.dto.AdminSummary;
import com.server.security.UserPrincipal;
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
