package com.example.git.repo.service;

import com.example.git.repo.client.GitClient;
import com.example.git.repo.model.GitRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GitRepoServiceTest {

    @Mock
    private GitClient gitClient;

    private GitRepoService gitRepoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gitRepoService = new GitRepoService(gitClient);
    }

    @Test
    void getRepoDetails_PositiveTest() {

        GitRepo mockRepo = new GitRepo();
        mockRepo.setFull_name("example-repo");
        mockRepo.setDescription("This is an example repository");
        mockRepo.setClone_url("https://github.com/example/example-repo");

        when(gitClient.getRepoDetails(anyString(), anyString())).thenReturn(mockRepo);

        GitRepo result = gitRepoService.getRepoDetails("example", "example-repo");

        assertEquals("example-repo", result.getFull_name());
        assertEquals("This is an example repository", result.getDescription());
        assertEquals("https://github.com/example/example-repo", result.getClone_url());
    }

    @Test
    void getRepoDetails_NegativeTest() {

        when(gitClient.getRepoDetails(anyString(), anyString())).thenThrow(new RuntimeException("Service unavailable"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gitRepoService.getRepoDetails("example", "non-existent-repo");
        });

        assertEquals("Service unavailable", exception.getMessage());
    }
}

