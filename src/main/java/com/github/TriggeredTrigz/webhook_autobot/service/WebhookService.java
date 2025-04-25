package com.github.TriggeredTrigz.webhook_autobot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookRequest;
import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookResponse;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class WebhookService {

    private final Dotenv dotenv;
    
    @Autowired
    private RestTemplate restTemplate;

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook";

    public WebhookService() {
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
    }
    
    @Retryable(maxAttempts = 4)
    public WebhookResponse callGenerateWebhook() {
        WebhookRequest request = new WebhookRequest(
            dotenv.get("NAME"), 
            dotenv.get("REG_NO"), 
            dotenv.get("EMAIL")
        );

        return restTemplate.postForObject(
            GENERATE_WEBHOOK_URL,
            request,
            WebhookResponse.class
        );
    }
}
