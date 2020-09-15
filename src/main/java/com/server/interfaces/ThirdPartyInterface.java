package com.server.interfaces;

import com.server.dto.SignUpThirdPartyRequest;
import com.server.dto.ThirdPartySummary;

import java.util.List;

public interface ThirdPartyInterface {
    ThirdPartySummary getThirdPartyUserById(Long id);
    List<ThirdPartySummary> getThirdPartyUsers();
    Long createThirdPartyUser(SignUpThirdPartyRequest thirdPartyDTO);
}
