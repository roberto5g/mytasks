package com.mytasks.app.service.impl;

import com.mytasks.app.model.Role;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.RoleRepository;
import com.mytasks.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleDefault(){
        return roleRepository.findByName("USER");
    }

    @Override
    public List<Role> getRolesByUserId(Long id) {
        return roleRepository.findByUser(id);
    }
}
