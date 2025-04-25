package com.github.TriggeredTrigz.webhook_autobot.dto;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private List<Integer> follows;
}
