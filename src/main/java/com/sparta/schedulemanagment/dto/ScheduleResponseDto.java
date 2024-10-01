package com.sparta.schedulemanagment.dto;

import com.sparta.schedulemanagment.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String userName;
    private String password;
    private String updateDate;
    private String contents;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.password = schedule.getPassword();
        this.updateDate = schedule.getUpdateDate();
        this.contents = schedule.getContents();
    }
}
