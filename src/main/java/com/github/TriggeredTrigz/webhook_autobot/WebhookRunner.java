package com.github.TriggeredTrigz.webhook_autobot;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.TriggeredTrigz.webhook_autobot.dto.User;
import com.github.TriggeredTrigz.webhook_autobot.dto.WebhookResponse;
import com.github.TriggeredTrigz.webhook_autobot.service.WebhookService;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class WebhookRunner implements CommandLineRunner {

    private final Dotenv dotenv;
    
    private final WebhookService webhookService;

    public WebhookRunner(WebhookService webhookService) {
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) {
        // Calls the API to get the webhook details
        WebhookResponse response = webhookService.callGenerateWebhook();

        // Gets user nested list
        List<User> users = response.getData().getUsers().getUsers();

        // Use the solver
        List<List<Integer>> outcome = webhookService.processUser(users);

        // Send to webhook
        webhookService.sendToWebhook(
            response.getWebhook(),
            response.getAccessToken(),
            Map.of("regNo", dotenv.get("REG_NO"), "outcome", outcome)
        );

    }

}
