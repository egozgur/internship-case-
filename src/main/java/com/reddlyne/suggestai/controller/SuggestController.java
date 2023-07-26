package com.reddlyne.suggestai.controller;

import com.reddlyne.suggestai.controller.request.SuggestRequest;
import com.reddlyne.suggestai.controller.response.SuggestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SuggestController {

    @PostMapping("/suggest")
    public ResponseEntity<SuggestResponse> getMessage(@RequestBody SuggestRequest suggestRequest) {
        System.out.println("received: " + suggestRequest.getText());
        //ask python chagpt service.
        //get response from service.
        //return response.
        SuggestResponse reply = new SuggestResponse("");
        return ResponseEntity.ok(reply);
    }
}
