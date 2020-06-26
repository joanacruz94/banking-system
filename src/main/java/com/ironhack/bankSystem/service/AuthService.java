package com.ironhack.bankSystem.service;

import com.ironhack.bankSystem.dto.JwtAuthenticationResponse;
import com.ironhack.bankSystem.dto.LoginRequest;
import com.ironhack.bankSystem.dto.SignUpRequest;
import com.ironhack.bankSystem.enums.SystemRole;
import com.ironhack.bankSystem.exceptions.AppException;
import com.ironhack.bankSystem.exceptions.ConflictException;
import com.ironhack.bankSystem.model.Role;
import com.ironhack.bankSystem.model.User;
import com.ironhack.bankSystem.repository.RoleRepository;
import com.ironhack.bankSystem.repository.UserRepository;
import com.ironhack.bankSystem.security.JwtTokenProvider;
import com.ironhack.bankSystem.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // log.info("User with [email: {}] has logged in", userPrincipal.getEmail());

        return new JwtAuthenticationResponse(jwt);
    }

    public Long registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ConflictException("Email [email: " + signUpRequest.getUsername() + "] is already taken");
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(SystemRole.ACCOUNTHOLDER)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));

        user.setRoles(Collections.singleton(userRole));

        // log.info("Successfully registered user with [email: {}]", user.getUsername());

        return userRepository.save(user).getId();
    }
}
