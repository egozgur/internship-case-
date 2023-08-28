package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.controller.SuggestController;
import com.reddlyne.suggestai.controller.request.SuggestRequest;
import com.reddlyne.suggestai.controller.response.SuggestResponse;
import com.reddlyne.suggestai.exception.UnexpectedAIFailure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SuggestControllerTest {

    private SuggestController suggestController;
    private SuggestService suggestService;
    private RestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        suggestService = mock(SuggestService.class);
        suggestController = new SuggestController(suggestService);
    }

    @Test
    void testGetMessage_Success() {
        SuggestRequest request = new SuggestRequest("Message");
        SuggestResponse response = new SuggestResponse("Response");

        when(suggestService.askToGPT(request.getMessage())).thenReturn(response);

        ResponseEntity<SuggestResponse> responseEntity = suggestController.getMessage(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(suggestService, times(1)).askToGPT(request.getMessage());
    }

}
