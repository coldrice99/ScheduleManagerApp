package com.sparta.schedulemanagment.dto;

import com.sparta.schedulemanagment.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String scheduled;
    private String contents;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.scheduled = schedule.getScheduled();
        this.contents = schedule.getContents();
    }
}
