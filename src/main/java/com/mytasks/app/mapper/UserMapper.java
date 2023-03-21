package com.mytasks.app.mapper;

import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.dto.UserResponseTask;
import com.mytasks.app.model.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public static UserResponseTask toUserResponseTask(User user) {
        UserResponseTask userResponseTask = new UserResponseTask();
        userResponseTask.setId(user.getId());
        userResponseTask.setName(user.getName());
        userResponseTask.setEmail(user.getEmail());
        return userResponseTask;
    }

    public static User toEntity(UserResponse userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}

