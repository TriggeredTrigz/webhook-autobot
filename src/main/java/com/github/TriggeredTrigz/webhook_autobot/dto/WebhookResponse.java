package com.github.TriggeredTrigz.webhook_autobot.dto;

import java.util.List;

import lombok.Data;

@Data
public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private UserData data;
}

@Data
class UserData {
    // private List<User> users;
    private UserList users;
    private Integer n;          // for question 2
    private Integer findId;     // for question 2
}

@Data
class UserList {
    private List<User> users;
}