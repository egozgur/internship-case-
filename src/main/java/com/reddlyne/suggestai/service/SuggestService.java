package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.controller.request.SuggestRequest;
import com.reddlyne.suggestai.controller.response.SuggestResponse;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SuggestService {

    private final RestTemplate restTemplate;

    @Value("${suggestai-engine.host}") // Changed to "suggestai-engine"
    private String suggestEngineHost;

    @Value("${suggestai-engine.port}") // Changed to "suggestai-engine"
    private String suggestEnginePort;


    public SuggestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SuggestResponse askToGPT(String receivedMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SuggestRequest flaskRequest = new SuggestRequest(receivedMessage);

        HttpEntity<SuggestRequest> requestEntity = new HttpEntity<>(flaskRequest, headers);

        ResponseEntity<SuggestResponse> responseEntity;
        try {
            String url = "http://" + suggestEngineHost + ":" + suggestEnginePort + "/";
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SuggestResponse.class);
        } catch (HttpServerErrorException e) {
            throw new UnexpectedAIFailure("Something went wrong on AI engine side.", e);
        }

        return new SuggestResponse(responseEntity.getBody().getResponseText());
    }
}
