package com.mytasks.app.mapper;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {UserMapper.class, TaskMapper.class})
public interface TaskMapper {

    TaskMapper taskMapperInstance = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "statusTask", source = "status")
    TaskResponse toTaskResponse(Task task);

    default User map(Long value) {
        if (value == null) {
            return null;
        }
        User user = new User();
        user.setId(value);
        return user;
    }

    default Long map(Task task) {
        return task != null ? task.getId() : null;
    }

    default String map(User user) {
        return user != null ? user.getName() : null;
    }

    Task toEntity(TaskRequest taskRequest);

    default List<TaskResponse> toDTOList(List<Task> tasks) {
        List<TaskResponse> list = new ArrayList<>();
        tasks.forEach(d -> list.add(toTaskResponse(d)));
        return list;
    }

}
