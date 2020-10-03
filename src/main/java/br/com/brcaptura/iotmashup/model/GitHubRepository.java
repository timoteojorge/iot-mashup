package br.com.brcaptura.iotmashup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubRepository {

    private Long id;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    private String name;
}
