package com.mytasks.app.service;

import com.mytasks.app.dto.UserRequest;
import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.model.Role;
import com.mytasks.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    
    Page<UserResponse> findAll(Pageable pageable);

    UserResponse getUserById(Long userId);

    User getUserByEmail(String email);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    UserResponse createUser(UserRequest userRequest);

    User getUserLogged();

    List<Role> getUserRoles();

}
