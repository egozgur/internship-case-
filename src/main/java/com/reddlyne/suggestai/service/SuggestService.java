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

    @Value("${suggest.server.url}")
    private String flaskUrl;

    public SuggestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SuggestResponse askToGPT(String receivedMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SuggestRequest flaskRequest = new SuggestRequest(receivedMessage);

        HttpEntity<SuggestRequest> requestEntity = new HttpEntity<>(flaskRequest, headers);

        ResponseEntity<SuggestResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, SuggestResponse.class);
        } catch (HttpServerErrorException e) {
            throw new UnexpectedAIFailure("Something went wrong on AI engine side.", e);
        }

        SuggestResponse response = responseEntity.getBody();
        return new SuggestResponse(response.getResponseText());
    }
}
