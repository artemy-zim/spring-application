package com.example.springapplication.service;

import com.example.springapplication.dto.UserDto;
import com.example.springapplication.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();
}
