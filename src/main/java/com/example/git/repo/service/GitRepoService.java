package com.example.git.repo.service;

import com.example.git.repo.client.GitClient;
import com.example.git.repo.model.GitRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class GitRepoService {

    private final GitClient gitClient;

    @Autowired
    public GitRepoService(GitClient gitClient) {
        this.gitClient = gitClient;
    }

    public GitRepo getRepoDetails(String owner, String repo) {
        return gitClient.getRepoDetails(owner, repo);
    }
}
