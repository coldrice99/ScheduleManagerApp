package com.sparta.schedulemanagment.entity;

import com.sparta.schedulemanagment.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 생성해주는 역할
public class Schedule {
    private Long id;
    private String scheduled;
    private String contents;


    public Schedule(ScheduleRequestDto requestDto) {
        this.scheduled = requestDto.getScheduled();
        this.contents = requestDto.getContents();
    }
}
