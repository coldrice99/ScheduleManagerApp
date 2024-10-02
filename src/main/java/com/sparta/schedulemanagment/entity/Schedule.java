
// Schedule.java
package com.sparta.schedulemanagment.entity;

import com.sparta.schedulemanagment.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동으로 생성해주는 역할
public class Schedule {
    private Long id;
    private String userName;
    private String password;
    private String contents;
    private LocalDateTime updateDate; // LocalDateTime 타입으로 변경


    public Schedule(ScheduleRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
        this.updateDate = LocalDateTime.now(); // 서버에서 자동으로 현재 시간 설정
    }

    public void update(ScheduleRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
        this.updateDate = LocalDateTime.now(); // 수정 시에도 현재 시간으로 업데이트
    }
}
