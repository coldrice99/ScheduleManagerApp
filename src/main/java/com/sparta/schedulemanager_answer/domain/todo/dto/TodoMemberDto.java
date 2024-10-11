package com.sparta.schedulemanager_answer.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoMemberDto {
    private Long id;
    private Long memberId;
    private String title;
    private String username;
    private String email;
    private String description;
    private String createdAt;
    private String updatedAt;
}
