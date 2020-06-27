package com.ironhack.server.service;

import com.ironhack.server.dto.*;
import com.ironhack.server.enums.RoleName;
import com.ironhack.server.exceptions.AppException;
import com.ironhack.server.exceptions.ConflictException;
import com.ironhack.server.model.*;
import com.ironhack.server.repository.*;
import com.ironhack.server.security.JwtTokenProvider;
import com.ironhack.server.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private AccountHolderRepository accountHolderRepository;
    private ThirdPartyRepository thirdPartyRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, AdminRepository adminRepository, AccountHolderRepository accountHolderRepository, ThirdPartyRepository thirdPartyRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.accountHolderRepository = accountHolderRepository;
        this.thirdPartyRepository = thirdPartyRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public JwtAuthenticationResponse authenticateUser(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // log.info("User with [email: {}] has logged in", userPrincipal.getEmail());

        return new JwtAuthenticationResponse(jwt);
    }

    public Long registerAdmin(SignUpAdminRequest signUpAdminRequest) {
        if(userRepository.existsByEmail(signUpAdminRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpAdminRequest.getEmail() + "] is already taken");
        }

        Admin admin = new Admin(signUpAdminRequest.getName(), signUpAdminRequest.getEmail(), signUpAdminRequest.getPassword());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        admin.setRoles(Collections.singleton(userRole));

        // log.info("Successfully registered user with [email: {}]", user.getUsername());

        return adminRepository.save(admin).getId();
    }

    public Long registerAccountHolder(SignUpAccountHolderRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpRequest.getEmail() + "] is already taken");
        }

        Address address = new Address(signUpRequest.getCity(), signUpRequest.getCountry(), signUpRequest.getStreetAddress(), signUpRequest.getHouseNumber(), signUpRequest.getZipCode());
        String[] dateOfBirth = signUpRequest.getDateOfBirth().split("-");
        LocalDate birthOfHolder = LocalDate.of(Integer.parseInt(dateOfBirth[2]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
        AccountHolder accountHolder = new AccountHolder(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword(), birthOfHolder, address);
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ACCOUNTHOLDER)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        accountHolder.setRoles(Collections.singleton(userRole));

        // log.info("Successfully registered user with [email: {}]", user.getUsername());

        return accountHolderRepository.save(accountHolder).getId();
    }

    public JwtAuthenticationResponse authenticateThirdParty(ThirdPartyLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getHashedKey()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // log.info("User with [email: {}] has logged in", userPrincipal.getEmail());

        return new JwtAuthenticationResponse(jwt);
    }

    public Long registerThirdParty(SignUpThirdPartyRequest thirdPartyRequest) {
        if(userRepository.existsByEmail(thirdPartyRequest.getEmail())) {
            throw new ConflictException("Email [email: " + thirdPartyRequest.getEmail() + "] is already taken");
        }

        ThirdParty thirdParty = new ThirdParty(thirdPartyRequest.getName(), thirdPartyRequest.getEmail(), thirdPartyRequest.getPassword(), thirdPartyRequest.getPassword());
        thirdParty.setPassword(passwordEncoder.encode(thirdPartyRequest.getPassword()));
        thirdParty.setHashedKey(passwordEncoder.encode(thirdPartyRequest.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_THIRDPARTY)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        thirdParty.setRoles(Collections.singleton(userRole));

        // log.info("Successfully registered user with [email: {}]", user.getUsername());

        return thirdPartyRepository.save(thirdParty).getId();
    }
}
