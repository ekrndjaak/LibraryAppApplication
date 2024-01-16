package com.group.libraryapp.repository.user;

import org.apache.coyote.Request;
import org.springframework.jdbc.core.JdbcTemplate;

//sql를 사용해 실제 DB와의 통신을 담당하낟.
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id) {
        String readSql = " * FROM user WHERE id = ?"; //유저가 존재하는지 SELECT 통하여 확인
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id){
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }
}
