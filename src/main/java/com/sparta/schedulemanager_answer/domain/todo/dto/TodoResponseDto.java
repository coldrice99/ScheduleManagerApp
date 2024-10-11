package com.sparta.schedulemanager_answer.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String username;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
}
