package com.server.interfaces;

import com.server.dto.JwtAuthenticationResponse;
import com.server.dto.ThirdPartyLoginRequest;
import com.server.dto.UserLoginRequest;

public interface AuthInterface {
    JwtAuthenticationResponse loginUser(UserLoginRequest loginRequest);
    JwtAuthenticationResponse loginThirdParty(ThirdPartyLoginRequest loginRequest);
}
