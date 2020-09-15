package com.server.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSummary {
    private Long id;
    private String name;
    private String email;
}