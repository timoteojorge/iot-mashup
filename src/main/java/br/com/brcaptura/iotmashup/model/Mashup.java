package br.com.brcaptura.iotmashup.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Mashup {
    private GitHubRepository repository;
    private List<Tweet> tweetList;
}
