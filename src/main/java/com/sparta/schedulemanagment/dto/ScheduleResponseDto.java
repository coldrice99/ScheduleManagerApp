
// ScheduleResponseDto.java
package com.sparta.schedulemanagment.dto;

import com.sparta.schedulemanagment.entity.Schedule;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String userName;
    private String contents;
    private String updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.contents = schedule.getContents();

        // LocalDateTime을 String으로 변환하여 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.updateDate = schedule.getUpdateDate().format(formatter);
    }

    public ScheduleResponseDto(Long id, String userName, String updateDate, String contents) {
        this.id = id;
        this.userName = userName;
        this.updateDate = updateDate;
        this.contents = contents;
    }
}
