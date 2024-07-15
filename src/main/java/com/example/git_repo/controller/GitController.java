package com.example.git_repo.controller;

import com.example.git_repo.client.GitClient;
import com.example.git_repo.model.GitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitController {

    @Autowired
    private GitClient gitClient;

    @GetMapping("/repositories/{owner}/{repo}")
    public GitRepo getRepoDetails(@PathVariable String owner, @PathVariable String repo) {
        return gitClient.getRepoDetails(owner, repo);
    }
}

