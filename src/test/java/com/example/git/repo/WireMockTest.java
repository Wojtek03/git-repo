package com.example.git.repo;

import com.example.git.repo.client.GitClient;
import com.example.git.repo.model.GitRepo;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.RetryableException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WireMockTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private ApplicationContext context;

    private GitClient gitClient;

    @BeforeAll
    public static void setupServer() {
        wireMockServer = new WireMockServer(8888); // Port 8888 for WireMockServer
        wireMockServer.start();
        configureFor("localhost", 8888); // Configuring WireMock to use localhost:8888
    }

    @BeforeEach
    public void setupClient() {
        gitClient = new FeignClientBuilder(context)
                .forType(GitClient.class, "githubClient")
                .url("http://localhost:8888") // Using WireMockServer's URL
                .build();
    }

    @Test
    public void getRepoDetails_RepoExists_RepoDetailsReturned() {
        stubFor(get(urlEqualTo("/repos/owner/repo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"full_name\": \"example/repo\", \"description\": \"This is a description.\", " +
                                "\"clone_url\": \"https://github.com/example/repo\", \"stargazers_count\": 100, \"created_at\": \"2024-01-01T00:00:00Z\" }")));

        GitRepo result = gitClient.getRepoDetails("owner", "repo");

        assertEquals("example/repo", result.getFull_name());
        assertEquals("This is a description.", result.getDescription());
        assertEquals("https://github.com/example/repo", result.getClone_url());
        assertEquals(100, result.getStargazers_count());
        assertEquals("2024-01-01T00:00:00Z", result.getCreated_at());
    }

    @Test
    public void getRepoDetails_ServiceUnavailable_ReturnsException() {
        stubFor(get(urlEqualTo("/repos/owner/service-unavailable"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Service is unavailable\" }")));

        RetryableException exception = assertThrows(RetryableException.class, () -> {
            gitClient.getRepoDetails("owner", "service-unavailable");
        });

        assertTrue(exception.getMessage().contains("Service is unavailable"));
    }

    @AfterAll
    public static void teardownServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}