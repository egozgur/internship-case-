package com.reddlyne.suggestai.controller;

import com.reddlyne.suggestai.controller.request.SuggestRequest;
import com.reddlyne.suggestai.controller.response.SuggestResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class SuggestController {

    @PostMapping("/suggest")
    public ResponseEntity<SuggestResponse> getMessage(@RequestBody SuggestRequest suggestRequest) {
        String receivedMessage = suggestRequest.getMessage();
        System.out.println("received: " + receivedMessage);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String flaskUrl = "http://127.0.0.1:5001/";
        SuggestRequest flaskRequest = new SuggestRequest(receivedMessage);
        HttpEntity<SuggestRequest> requestEntity = new HttpEntity<>(flaskRequest, headers);
        ResponseEntity<SuggestResponse> responseEntity = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, SuggestResponse.class);

        SuggestResponse response = responseEntity.getBody();

        SuggestResponse reply = new SuggestResponse(response.getResponseText());
        return ResponseEntity.ok(reply);

    }
}
