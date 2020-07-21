package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.*;

public interface AuthInterface {
    JwtAuthenticationResponse loginUser(UserLoginRequest loginRequest);
    JwtAuthenticationResponse loginThirdParty(ThirdPartyLoginRequest loginRequest);
}
