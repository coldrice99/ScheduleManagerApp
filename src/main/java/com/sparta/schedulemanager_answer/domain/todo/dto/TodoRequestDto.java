package com.sparta.schedulemanager_answer.domain.todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String username;
    private String title;
    private String password;
    private String description;
    private String createdAt;
    private String updatedAt;
}
