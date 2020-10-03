package br.com.brcaptura.iotmashup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubSearchResponse {

    @JsonProperty("total_count")
    private Integer totalCount;
    private List<GitHubRepository> items;
}
