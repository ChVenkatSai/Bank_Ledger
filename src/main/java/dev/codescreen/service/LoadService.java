package dev.codescreen.service;

import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;

public interface LoadService {

    public void setLoadRequest(LoadRequest loadRequest);

    public LoadResponse getResponse();

}
