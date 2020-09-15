package com.server.service;

import com.server.dto.SignUpThirdPartyRequest;
import com.server.dto.ThirdPartySummary;
import com.server.enums.RoleName;
import com.server.exceptions.AppException;
import com.server.exceptions.ConflictException;
import com.server.exceptions.NotFoundException;
import com.server.model.Role;
import com.server.model.ThirdParty;
import com.server.repository.RoleRepository;
import com.server.repository.ThirdPartyRepository;
import com.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LogManager.getLogger(ThirdPartyService.class);

    public ThirdPartySummary findThirdPartyUserById(Long id) {
        LOGGER.info("Look for third party user with ID " + id);
        ThirdParty user = thirdPartyRepository.findById(id).orElseThrow(() -> new NotFoundException("Third party user with that ID doesn't exist"));
        return new ThirdPartySummary(user.getId(), user.getName(), user.getEmail(), user.getHashedKey());
    }

    public List<ThirdPartySummary> findAllThirdPartyUsers(){
        LOGGER.info("Look for all third party users");
        return thirdPartyRepository.findAll().stream().map(user ->
                new ThirdPartySummary(user.getId(), user.getName(), user.getEmail(), user.getHashedKey())).collect(Collectors.toList());
    }

    public Long registerThirdPartyUser(SignUpThirdPartyRequest thirdPartyRequest) {
        if(userRepository.existsByEmail(thirdPartyRequest.getEmail())) {
            throw new ConflictException("Email [email: " + thirdPartyRequest.getEmail() + "] is already taken");
        }

        ThirdParty thirdParty = new ThirdParty(thirdPartyRequest.getName(), thirdPartyRequest.getEmail(), thirdPartyRequest.getPassword(), thirdPartyRequest.getPassword());
        thirdParty.setPassword(passwordEncoder.encode(thirdPartyRequest.getPassword()));
        thirdParty.setHashedKey(passwordEncoder.encode(thirdPartyRequest.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_THIRDPARTY)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        thirdParty.setRoles(Collections.singleton(userRole));

        LOGGER.info("Successfully registered user with [email: {}]", thirdParty.getEmail());

        return thirdPartyRepository.save(thirdParty).getId();
    }
}
