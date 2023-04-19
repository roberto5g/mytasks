package com.mytasks.app.repository;

import com.mytasks.app.model.Role;
import com.mytasks.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
    List<Role> findByUser(@Param("userId") Long userId);
}
