package com.github.TriggeredTrigz.webhook_autobot.dto;

import lombok.Data;

@Data
public class UserData {
    private UserList users;
    private Integer n;          // for question 2
    private Integer findId;     // for question 2
}