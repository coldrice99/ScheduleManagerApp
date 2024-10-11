package com.sparta.schedulemanager_answer.domain.member.repository;

import com.sparta.schedulemanager_answer.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public Member findById(Long todoId) {
        String sql = "SELECT * FROM member WHERE id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                // rs to DAO
                return Member.from(rs);
            } else {
                return null;
            }
        }, todoId);
    }
}
