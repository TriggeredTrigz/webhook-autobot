package com.github.TriggeredTrigz.webhook_autobot.dto;

import lombok.Data;

@Data
public class WebhookRequest {
    private String name;
    private String regNo;
    private String email;

    public WebhookRequest(String name, String regNo, String email) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
    }
}
