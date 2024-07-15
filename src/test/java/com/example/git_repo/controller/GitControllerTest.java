package com.example.git_repo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.git_repo.client.GitClient;
import com.example.git_repo.model.GitRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc
public class GitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitClient gitClient;

    private GitRepo mockRepo;

    @BeforeEach
    public void setup() {
        mockRepo = new GitRepo();
        mockRepo.setFull_name("example/repo");
        mockRepo.setDescription("This is a description.");
        mockRepo.setClone_url("https://github.com/Wojtek03/git-repo");
        mockRepo.setStargazers_count(100);
        mockRepo.setCreated_at("2024-01-01T00:00:00Z");
    }

    @Test
    public void GetRepoDetails_RepoExists_RepoDetailsReturned() throws Exception {
        when(gitClient.getRepoDetails("owner", "repo")).thenReturn(mockRepo);

        mockMvc.perform(get("/repositories/owner/repo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.full_name").value("example/repo"))
                .andExpect(jsonPath("$.description").value("This is a description."))
                .andExpect(jsonPath("$.clone_url").value("https://github.com/Wojtek03/git-repo"))
                .andExpect(jsonPath("$.stargazers").value(100))
                .andExpect(jsonPath("$.created_at").value("2024-01-01T00:00:00Z"));
    }

    @Test
    public void GetRepoDetails_RepoDoesNotExist_ReturnsException() throws Exception {
        String owner = "owner";
        String repositoryName = "repo";

        when(gitClient.getRepoDetails(owner, repositoryName))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Repository not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/" + owner + "/" + repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Repository not found"));
    }
}

