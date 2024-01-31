package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private  final UserService userService;
    //private  final List<User> users = new ArrayList<>(); * 이문장은 메모리에 저장한다 -> 서버를 온오프시 데이터 없어짐
private final JdbcTemplate jdbcTemplate;

public UserController(JdbcTemplate jdbcTemplate){

    this.jdbcTemplate = jdbcTemplate;
    this.userService = new UserService(jdbcTemplate);
}
    @PostMapping("/user") // Post / user
    public void saveUser(@RequestBody UserCreateRequest request) {
    //users.add(new User(request.getName(), request.getAge()));
    userService.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
    /*List<UserResponse> responses = new ArrayList<>();
    for( int i = 0; i < users.size(); i++){
        responses.add(new UserResponse(i + 1, users.get(i)));
        }
    return responses;*/
    return userService.getUsers();
    }
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
        userService.updateUser(jdbcTemplate, request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
    userService.deleteUser(name);
    }

    @GetMapping("/user/error-test")
    public void errorTest(){
    throw new IllegalArgumentException();
    }

}