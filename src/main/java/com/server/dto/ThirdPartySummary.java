package com.server.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPartySummary {
    private Long id;
    private String name;
    private String email;
    private String secretKey;
}
