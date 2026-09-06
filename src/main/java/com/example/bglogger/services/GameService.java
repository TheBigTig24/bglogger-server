package com.example.bglogger.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;

import com.example.bglogger.dto.ExternalGameDTO;
import com.example.bglogger.dto.ExternalGameWrapper;
import com.example.bglogger.models.Game;
import com.example.bglogger.repositories.GameRepository;

@Service
public class GameService {
    
    private final GameRepository gameRepository;
    private final RestTemplate restTemplate;

    @Value("${bgg.xml.api.url}")
    private String apiBaseUrl;

    @Value("${bgg.xml.api.token}")
    private String apiToken;

    public GameService(
        GameRepository gameRepository,
        RestTemplate restTemplate
    ) {
        this.gameRepository = gameRepository;
        this.restTemplate = restTemplate;
    }

    public ExternalGameWrapper getExternalGame(Integer externalId) {
        String url = apiBaseUrl + "thing?id=" + externalId + "&stats=1";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        headers.setBearerAuth(apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ExternalGameWrapper> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            ExternalGameWrapper.class
        );

        ExternalGameWrapper body = response.getBody();

        return body;
    }

    public String getExternalGameByName(String query) {
        String url = apiBaseUrl + "search?query=" + query + "&type=boardgame";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        headers.setBearerAuth(apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            String.class
        );

        return response.getBody();
    }

    public String getStringResponse(Integer gameId) {
        String url = apiBaseUrl + "thing?id=" + gameId + "&stats=1";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        headers.setBearerAuth(apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            String.class
        );

        return response.getBody();
    }
}
