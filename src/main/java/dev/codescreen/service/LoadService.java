package dev.codescreen.service;

import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;

/*
Inteface to the class that implements the service that handles
load requests.
 */
public interface LoadService {

    public void setLoadRequest(LoadRequest loadRequest);

    //Gets the appropriate response
    public LoadResponse getResponse();

}
