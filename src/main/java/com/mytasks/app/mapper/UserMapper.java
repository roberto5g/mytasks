package com.mytasks.app.mapper;

import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.dto.UserResponseTask;
import com.mytasks.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UserMapper {

    private UserMapper(){}

    public static UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoles(user.getRoles());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public static Page<UserResponse> toPageUserResponse(Page<User> userPage, Pageable pageable) {
        List<UserResponse> userResponse = userPage.stream().map( user ->
                UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build()
        ).toList();
        return new PageImpl<>(userResponse, pageable, userPage.getTotalElements());
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

