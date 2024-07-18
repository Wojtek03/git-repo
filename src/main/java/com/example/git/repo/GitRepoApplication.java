package com.example.git.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GitRepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitRepoApplication.class, args);
	}

}
