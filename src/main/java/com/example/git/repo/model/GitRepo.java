package com.example.git.repo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GitRepo {
    private String full_name;
    private String description;
    private String clone_url;
    private int stargazers_count;
    private String created_at;
}
