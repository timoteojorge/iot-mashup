package br.com.brcaptura.iotmashup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterSearchResponse {

    private List<Tweet> statuses;
}
