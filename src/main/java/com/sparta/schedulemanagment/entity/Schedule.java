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
    private String userName;
    private String password;
    private String updateDate;
    private String contents;


    public Schedule(ScheduleRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.updateDate = requestDto.getUpdateDate();
        this.contents = requestDto.getContents();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.updateDate = requestDto.getUpdateDate();
        this.contents = requestDto.getContents();
    }
}
