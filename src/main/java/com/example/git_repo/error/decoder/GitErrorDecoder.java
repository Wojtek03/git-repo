package com.example.git_repo.error.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GitErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable - 503");
        }
        return new Exception("Generic error");
    }
}

