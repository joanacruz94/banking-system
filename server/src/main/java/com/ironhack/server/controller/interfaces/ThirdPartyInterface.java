package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.SignUpThirdPartyRequest;
import com.ironhack.server.dto.ThirdPartySummary;
import com.ironhack.server.model.ThirdParty;

import java.util.List;

public interface ThirdPartyInterface {
    ThirdPartySummary getThirdPartyUserById(Long id);
    List<ThirdPartySummary> getThirdPartyUsers();
    Long createThirdPartyUser(SignUpThirdPartyRequest thirdPartyDTO);
}
