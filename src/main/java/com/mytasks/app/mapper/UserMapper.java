package com.mytasks.app.mapper;

import com.mytasks.app.dto.UserResponse;
import com.mytasks.app.dto.UserResponseTask;
import com.mytasks.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper userMapperInstance = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    default Page<UserResponse> toPageUserResponse(Page<User> pages){
        List<UserResponse> list = toListResponse(pages.getContent());
        return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());
    }

    default List<UserResponse> toListResponse(List<User> users){
        List<UserResponse> list = new ArrayList<>();
        users.forEach(d-> list.add(toUserResponse(d)));
        return list;
    }

    UserResponseTask toUserResponseTask(User user);

    User toEntity(UserResponse userDTO);
}

