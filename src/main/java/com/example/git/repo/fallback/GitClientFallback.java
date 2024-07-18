package com.example.git.repo.fallback;

import com.example.git.repo.client.GitClient;
import com.example.git.repo.model.GitRepo;
import org.springframework.stereotype.Component;

@Component
public class GitClientFallback implements GitClient {

    @Override
    public GitRepo getRepoDetails(String owner, String repo) {
        GitRepo fallbackRepo = new GitRepo();
        fallbackRepo.setFull_name("Repository not found");
        fallbackRepo.setDescription("Service is currently unavailable.");
        return fallbackRepo;
    }
}