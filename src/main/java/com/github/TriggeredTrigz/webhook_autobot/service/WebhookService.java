package com.github.TriggeredTrigz.webhook_autobot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.TriggeredTrigz.webhook_autobot.dto.User;
import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookRequest;
import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookResponse;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final Dotenv dotenv;
    
    @Autowired
    private RestTemplate restTemplate;
    private final MutualFollowersSolver solver;

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook";

    public WebhookService() {
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.solver = new MutualFollowersSolver();
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

    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 1000))
    public void sendToWebhook(String webhookURL, String accessToken, Map<String, Object> result) {
        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Build the request
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(result, headers);

            // Send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(
                webhookURL,
                request,
                String.class
            );

            // Log success
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Successfully sent to webhook!");
            } else {
                throw new RuntimeException("Webhook returned: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Webhook failed: " + e.getMessage());
            throw e; // Triggers retry
        }
    }
    
    public List<List<Integer>> processUser(List<User> users) {
        return solver.findMutualFollowers(users);
    }

}
