package com.example.git_repo.retryer;

import feign.Retryer;

import static java.util.concurrent.TimeUnit.SECONDS;

public class GitRetryer extends Retryer.Default {
    public GitRetryer() {
        super(100, SECONDS.toMillis(1), 5);
    }
}

