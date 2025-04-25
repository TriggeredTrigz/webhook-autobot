package com.github.TriggeredTrigz.webhook_autobot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookResponse;
import com.github.TriggeredTrigz.webhook_autobot.service.WebhookService;

@Component
public class WebhookRunner implements CommandLineRunner {
    
    private final WebhookService webhookService;

    public WebhookRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) {
        // Calls the API to get the webhook details
        WebhookResponse response = webhookService.callGenerateWebhook();

        // Prints response for testing
        System.out.println(
            "API Response Received:" + 
            "\nWebhook URL: " + response.getWebhook() + 
            "\nToken: " + response.getAccessToken()
            );
    }

}
