package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
//현재 유저가 있는지, 없는지 등을 확인하고 예외처리를 해준다.
public class UserService {

    private final UserRepository userRepository;

    public UserService(JdbcTemplate jdbcTemplate) {
        userRepository = new UserRepository(jdbcTemplate);
    }

    public void updateUser(JdbcTemplate jdbcTemplate, UserUpdateRequest request) {
        boolean isUserNotExist = userRepository.isUserNotExist(jdbcTemplate, request.getId());
        if(isUserNotExist){
            throw new IllegalArgumentException();
        }
        userRepository.updateUserName( request.getName(), request.getId());

    }

}
