package com.example.git_repo.client;

import com.example.git_repo.configuration.GitClientConfiguration;
import com.example.git_repo.fallback.GitClientFallback;
import com.example.git_repo.model.GitRepo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "githubClient",
        url = "https://api.github.com",
        fallback = GitClientFallback.class,
        configuration = GitClientConfiguration.class
)
public interface GitClient {

    @GetMapping("/repos/{owner}/{repo}")
    GitRepo getRepoDetails(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
