package dev.codescreen.service;

import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;

/*
Inteface to the class that implements the service that handles
authorization requests.
 */
public interface AuthorizationService {

    public void setAuthorizationRequest(AuthorizationRequest authorizationRequest);

    //Gets the corresponding response.
    public AuthorizationResponse getResponse();
}
