
// ScheduleController.java
package com.sparta.schedulemanagment.controller;

import com.sparta.schedulemanagment.dto.ScheduleRequestDto;
import com.sparta.schedulemanagment.dto.ScheduleResponseDto;
import com.sparta.schedulemanagment.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController // @Controller + @ResponseBody. @ResponseBody : 메서드의 반환 값을 HTTP 응답 본문으로 처리
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 생성
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB에 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본키를 반환닫기 위한 객체

        String sql = "INSERT INTO schedules (userName, password, contents, updateDate) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, schedule.getUserName());
            ps.setString(2, schedule.getPassword());
            ps.setString(3, schedule.getContents());
            ps.setObject(4, schedule.getUpdateDate()); // LocalDateTime을 Object로 설정
            return ps;
        }, keyHolder);

        // DB insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    // 전체 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getAllSchedules() {
        // DB 조회
        String sql = "SELECT * FROM schedules ORDER BY updateDate DESC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String userName = rs.getString("userName");
                String updateDate = rs.getString("updateDate");
                String contents = rs.getString("contents");

                return new ScheduleResponseDto(id, userName, updateDate, contents);
            }
        });
    }

    // 선택 일정 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        // 해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findByID(id);
        if(schedule != null) {
            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    // 일정 수정
    @Transactional
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        //해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findByID(id);
        if(schedule != null) {
            // schedule 내용 수정 및 현재 시간으로 updateDate 설정
            // 기존 password는 유지하고 userName, contents만 수정
            schedule.update(requestDto); // updateDate도 현재 시간으로 업데이트

            String sql = "UPDATE schedules SET userName = ?, updateDate = NOW(), contents = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getUserName(), requestDto.getContents(), id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        // 해당 스케줄이 DB에 존재하는지 확인
        Schedule schedule = findByID(id);
        if (schedule != null) {
            // 패스워드가 null인지 체크
            if(schedule.getPassword() == null || passwordMap.get("password") == null) {
                throw new IllegalArgumentException("비밀번호가 설정되지 않았습니다.");
            }

            // 패스워드 검증
            String inputPassword = passwordMap.get("password");
            if(!schedule.getPassword().equals(inputPassword)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            // 해당 스케줄 삭제하기
            String sql = "DELETE FROM schedules WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    // JdbcTemplate을 사용하여 데이터베이스에서 특정 id를 가진 일정을 조회하는 메서드
    private Schedule findByID(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedules WHERE id = ?";

        // jdbcTemplate.query 메서드는 SQL 쿼리를 실행하는 역할
        // 첫 번째 매개변수 sql은 실행할 쿼리, 두 번째 매개변수 resultSet은 쿼리 결과를 처리하는 람다 함수, 세 번째 매개변수 id는 ?에 들어갈 값
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) { // resultSet.next()는 쿼리 결과로 반환된 데이터의 첫 번째 행을 가리킨다. 결과가 있으면 true를 반환하고, 없으면 false를 반환
                // 결과가 있으면 Schedule 객체를 생성하고 resultSet에서 가져온 데이터를 객체에 세팅
                Schedule schedule = new Schedule();
                schedule.setUserName(resultSet.getString("userName"));
                schedule.setPassword(resultSet.getString("password")); // 비밀번호 추가.

                // String으로 가져온 updateDate를 LocalDateTime으로 변환
                String updateDateString = resultSet.getString("updateDate");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime updateDate = LocalDateTime.parse(updateDateString, formatter);
                schedule.setUpdateDate(updateDate);

                schedule.setContents(resultSet.getString("contents"));
                return schedule;
            } else {
                return null; // 결과가 없음
            }
        },id);
    }


}
