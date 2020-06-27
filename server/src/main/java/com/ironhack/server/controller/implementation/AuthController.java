package com.ironhack.server.controller.implementation;

import com.ironhack.server.dto.*;
import com.ironhack.server.dto.*;
import com.ironhack.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse loginUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/sign-in/thirdParty")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse loginThirdParty(@Valid @RequestBody ThirdPartyLoginRequest loginRequest) {
        return authService.authenticateThirdParty(loginRequest);
    }

    @PostMapping("/sign-up/admin")
    @ResponseStatus(HttpStatus.OK)
    public Long registerAdmin(@Valid @RequestBody SignUpAdminRequest signUpRequest) {
        return authService.registerAdmin(signUpRequest);
    }

    @PostMapping("/sign-up/accountHolder")
    @ResponseStatus(HttpStatus.OK)
    public Long registerAccountHolder(@Valid @RequestBody SignUpAccountHolderRequest signUpRequest) {
        return authService.registerAccountHolder(signUpRequest);
    }

    @PostMapping("/sign-up/thirdParty")
    @ResponseStatus(HttpStatus.OK)
    public Long registerThirdParty(@Valid @RequestBody SignUpThirdPartyRequest signUpRequest) {
        return authService.registerThirdParty(signUpRequest);
    }

}
