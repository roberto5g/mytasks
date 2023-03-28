package com.mytasks.app.dto;

import com.mytasks.app.model.Role;
import lombok.*;

import java.util.Set;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
