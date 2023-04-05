package com.mytasks.app.service.impl;

import com.mytasks.app.model.Role;
import com.mytasks.app.repository.RoleRepository;
import com.mytasks.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleDefault(){
        return roleRepository.findByName("USER");
    }
}
