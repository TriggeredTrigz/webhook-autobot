package com.github.TriggeredTrigz.webhook_autobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class WebhookAutomationForBajajApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebhookAutomationForBajajApplication.class, args);
	}

}
