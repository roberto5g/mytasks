package com.mytasks.app.service.impl;

import com.mytasks.app.dto.UserRequest;
import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.exceptions.UserException;
import com.mytasks.app.exceptions.UserNotFoundException;
import com.mytasks.app.exceptions.UserUpdateException;
import com.mytasks.app.model.Role;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.UserRepository;
import com.mytasks.app.service.RoleService;
import com.mytasks.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.mytasks.app.mapper.UserMapper.userMapperInstance;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public Page<UserResponse> findAll(Pageable pageable){
        return userMapperInstance.toPageUserResponse(userRepository.findAll(pageable));
    }

    @Override
    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        return userMapperInstance.toUserResponse(user);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: "+email)
        );
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        Optional<User> userByEmail = userRepository.findByEmail(userRequest.getEmail());
        if (userByEmail.isPresent() && !user.getEmail().equals(userByEmail.get().getEmail())){
            throw new UserUpdateException("The email provided is already in use.");
        }
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        return userMapperInstance.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> userByEmail = userRepository.findByEmail(userRequest.getEmail());
        if (userByEmail.isPresent()){
            throw new UserException("The email provided is already in use.");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        List<Role> role = new ArrayList<>();
        role.add(roleService.getRoleDefault());
        user.setRoles(role);
        user.setCreatedAt(LocalDateTime.now());
        return userMapperInstance.toUserResponse(userRepository.save(user));
    }

    @Override
    public User getUserLogged() {
        UserResponse userResponse = getLoggedInUser();
        return userMapperInstance.toEntity(userResponse);
    }

    @Override
    public List<Role> getUserRoles() {
        return roleService.getRolesByUserId(getLoggedInUser().getId());
    }

    @Override
    public boolean isAdmin() {
        return getUserRoles().stream().anyMatch(role -> Objects.equals(role.getName(),"ADMIN"));
    }

    private UserResponse getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UserNotFoundException("User not found with email: "+authentication.getName())
        );
        return userMapperInstance.toUserResponse(user);
    }

}
