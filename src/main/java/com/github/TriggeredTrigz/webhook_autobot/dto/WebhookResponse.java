package com.github.TriggeredTrigz.webhook_autobot.dto;

import lombok.Data;

@Data
public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private UserData data;
}