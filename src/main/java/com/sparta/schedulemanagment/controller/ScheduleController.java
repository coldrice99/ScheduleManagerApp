package com.sparta.schedulemanagment.controller;

import com.sparta.schedulemanagment.dto.ScheduleRequestDto;
import com.sparta.schedulemanagment.dto.ScheduleResponseDto;
import com.sparta.schedulemanagment.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody. @ResponseBody : 메서드의 반환 값을 HTTP 응답 본문으로 처리
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();  // long: id , Schedule : 객체

    // 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        // DB에 저장
        scheduleList.put(schedule.getId(), schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    // 전체 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getAllSchedules() {
        // Map to List
        List<ScheduleResponseDto> responseList = scheduleList.values().stream()
                .map(ScheduleResponseDto::new).toList();

        return responseList;
    }

    // 일정 수정
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        //해당 메모리가 DB에 존재하는지 확인
        if (scheduleList.containsKey(id)) {
            // 해당 메모리 가져오기
            Schedule schedule = scheduleList.get(id);

            // 스케줄 수정
            schedule.update(requestDto);
            return schedule.getId();
        } else {
            throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
        }
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        // 해당 메모리가 DB에 존재하는지 확인
        if(scheduleList.containsKey(id)) {
            // 해당 스케줄 삭제하기
            scheduleList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("일정을 찾을 수 없습니다.");
        }
    }


}
