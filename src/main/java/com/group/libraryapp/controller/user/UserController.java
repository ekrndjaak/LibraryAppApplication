package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    //private  final List<User> users = new ArrayList<>(); * 이문장은 메모리에 저장한다 -> 서버를 온오프시 데이터 없어짐
private final JdbcTemplate jdbcTemplate;

public UserController(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
}
    @PostMapping("/user") // Post / user
    public void saveUser(@RequestBody UserCreateRequest request) {
    //users.add(new User(request.getName(), request.getAge()));
        String sql = "INSERT INTO user(name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
    /*List<UserResponse> responses = new ArrayList<>();
    for( int i = 0; i < users.size(); i++){
        responses.add(new UserResponse(i + 1, users.get(i)));
        }
    return responses;*/
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString(("name"));
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
    String readSql = " * FROM user WHERE id = ?"; //유저가 존재하는지 SELECT 통하여 확인
    boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
    if(isUserNotExist){
        throw new IllegalArgumentException();
    }
    String sql = "UPDATE user SET name = ? WHERE id = ?";
    jdbcTemplate.update(sql, request.getName(),request.getId());

    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
    String sql= "DELETE FROM user WHERE name = ?";
    jdbcTemplate.update(sql, name);
    }



}