package com.mytasks.app.service;

import com.mytasks.app.dto.UserRequest;
import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.exceptions.UserNotFoundException;
import com.mytasks.app.exceptions.UserUpdateException;
import com.mytasks.app.mapper.UserMapper;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        return UserMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        Optional<User> userByEmail = userRepository.findByEmail(userRequest.getEmail());
        if (userByEmail.isPresent()){
            if(!user.getEmail().equals(userByEmail.get().getEmail())){
                throw new UserUpdateException("The email provided is already in use.");
            }
        }
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));

    }
}
