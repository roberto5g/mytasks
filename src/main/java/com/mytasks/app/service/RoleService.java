package com.mytasks.app.service;

import com.mytasks.app.model.Role;
import com.mytasks.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleDefault(){
        return roleRepository.findByName("USER");
    }
}
