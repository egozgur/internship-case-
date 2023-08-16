package com.reddlyne.suggestai.controller;

import com.reddlyne.suggestai.controller.request.SuggestRequest;
import com.reddlyne.suggestai.controller.response.SuggestResponse;
import com.reddlyne.suggestai.service.SuggestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class SuggestController {

    private final SuggestService suggestService;
    public SuggestController(SuggestService suggestService) {
        this.suggestService = suggestService;
    }

    @PostMapping("/suggest")
    public ResponseEntity<SuggestResponse> getMessage(@RequestBody SuggestRequest suggestRequest) {
        String receivedMessage = suggestRequest.getMessage();
        System.out.println("received: " + receivedMessage);

        SuggestResponse reply = suggestService.getSuggestedResponse(receivedMessage);
        return ResponseEntity.ok(reply);
    }
}
