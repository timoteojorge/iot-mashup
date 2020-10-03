package br.com.brcaptura.iotmashup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

    private Long id;
    @JsonProperty("created_at")
    private String createdAt;
    private String text;
}
