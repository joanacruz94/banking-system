package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.AuthInterface;
import com.ironhack.server.dto.*;
import com.ironhack.server.service.AccountHolderService;
import com.ironhack.server.service.AdminService;
import com.ironhack.server.service.AuthService;
import com.ironhack.server.service.ThirdPartyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    @Autowired
    AuthService authService;

    @Autowired
    AccountHolderService accountHolderService;

    @Autowired
    ThirdPartyService thirdPartyService;

    @Autowired
    AdminService adminService;

    @ApiOperation(value = "Login for admin and account holder. Method returns the auth token that you will use in the header of all secured routes.")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse loginUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @ApiOperation(value = "Login for third party. Method returns the auth token that you will use in the header of all secured routes.")
    @PostMapping("/sign-in/thirdParty")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse loginThirdParty(@Valid @RequestBody ThirdPartyLoginRequest loginRequest) {
        return authService.authenticateThirdParty(loginRequest);
    }
}
