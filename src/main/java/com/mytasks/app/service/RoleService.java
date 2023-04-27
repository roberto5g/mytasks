package com.mytasks.app.service;

import com.mytasks.app.model.Role;
import com.mytasks.app.model.User;

import java.util.List;


public interface RoleService {
    Role getRoleDefault();
    List<Role> getRolesByUserId(Long id);
}
