package com.example.git_repo.fallback;

import com.example.git_repo.client.GitClient;
import com.example.git_repo.model.GitRepo;
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

