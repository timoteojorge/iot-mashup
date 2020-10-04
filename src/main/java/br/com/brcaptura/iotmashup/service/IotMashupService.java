package br.com.brcaptura.iotmashup.service;

import br.com.brcaptura.iotmashup.model.GitHubRepository;
import br.com.brcaptura.iotmashup.model.GitHubSearchResponse;
import br.com.brcaptura.iotmashup.model.Mashup;
import br.com.brcaptura.iotmashup.model.TwitterSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IotMashupService {

    Logger logger = LoggerFactory.getLogger(IotMashupService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String githubApiURL;

    @Value("${twitter.api.secret}")
    private String twitterApiSecret;

    @Value("${twitter.api.key}")
    private String twitterApiKey;

    @Value("${twitter.auth.url}")
    private String twitterAuthURL;

    @Value("${twitter.search.url}")
    private String twitterSearchURL;

    private String bearerToken;

    private List<Mashup> mashupList;

    public List<Mashup> buildMashups() {
        logger.info("Buscando projetos...");
        ResponseEntity<GitHubSearchResponse> gitHubSearchResponseResponseEntity
                = restTemplate.exchange(encode(githubApiURL + "?q=Internet+of+Things"), HttpMethod.GET, null, GitHubSearchResponse.class);
        GitHubSearchResponse gitHubSearchResponse = gitHubSearchResponseResponseEntity.getBody();
        logger.info(gitHubSearchResponse.getTotalCount() + " projetos encontrados no github");

        logger.info("Buscando resultados de tweets...");
        mashupList = new ArrayList<>();
        return gitHubSearchResponse.getItems().stream().limit(5).map(this::getTwitterMatches).collect(Collectors.toList());
    }

    private Mashup getTwitterMatches(GitHubRepository repo) {
        try {
            if (bearerToken == null) {
                bearerToken = this.authenticateWithTwitter();
            }
            Mashup mashup = Mashup.builder().repository(repo).tweetList(new ArrayList<>()).build();

            ResponseEntity<TwitterSearchResponse> twitterSearchResponse =
                    restTemplate.exchange(encode(twitterSearchURL + "?q=" + repo.getName()), HttpMethod.GET, new HttpEntity<String>(createTwitterAuthorizationHeaders(bearerToken)), TwitterSearchResponse.class);
            TwitterSearchResponse twitterSearchResponseBody = twitterSearchResponse.getBody();
            logger.info(twitterSearchResponseBody.getStatuses().size() + " resultados encontrados para o projeto " + repo.getName());
            mashup.getTweetList().addAll(twitterSearchResponseBody.getStatuses());
            return mashup;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Nao foi possivel autenticar com o twitter!");
        }
        return null;
    }

    private String authenticateWithTwitter() throws JsonProcessingException {
        logger.info("Autenticando com twitter...");
        ResponseEntity<String> twitterAuthResponse = restTemplate.exchange
                (twitterAuthURL, HttpMethod.POST, new HttpEntity<String>(createTwitterAuthenticationHeaders()), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(twitterAuthResponse.getBody());
        return root.path("access_token").asText();
    }

    HttpHeaders createTwitterAuthorizationHeaders(String bearerToken) {
        return new HttpHeaders() {{
            String authHeader = "Bearer " + bearerToken;
            set("Authorization", authHeader);
        }};
    }

    HttpHeaders createTwitterAuthenticationHeaders() {
        return new HttpHeaders() {{
            String auth = twitterApiKey + ":" + twitterApiSecret;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    private String encode(String decoded) {
        return UriComponentsBuilder.fromUriString(decoded).encode().toUriString();
    }
}
