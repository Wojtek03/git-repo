package com.example.git.repo.configuration;

import com.example.git.repo.error.decoder.GitErrorDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class GitClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GitErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 5);
    }
}
