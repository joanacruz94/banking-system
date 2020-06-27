package com.ironhack.server.dto;

import com.ironhack.server.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSummary {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
}