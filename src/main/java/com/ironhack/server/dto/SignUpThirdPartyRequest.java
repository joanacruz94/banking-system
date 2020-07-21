package com.ironhack.server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpThirdPartyRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
