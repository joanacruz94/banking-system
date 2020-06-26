package com.ironhack.bankSystem.service;

import com.ironhack.bankSystem.model.ThirdParty;
import com.ironhack.bankSystem.repository.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
}
