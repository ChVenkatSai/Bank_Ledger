package dev.codescreen.service;

import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;

public interface AuthorizationService {

    public void setAuthorizationRequest(AuthorizationRequest authorizationRequest);

    public AuthorizationResponse getResponse();
}
