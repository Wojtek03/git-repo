package com.example.git_repo.configuration;

import com.example.git_repo.error.decoder.GitErrorDecoder;
import com.example.git_repo.retryer.GitRetryer;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GitClientConfiguration {

    @Bean
    public Retryer retryer() {
        return new GitRetryer();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GitErrorDecoder();
    }
}
