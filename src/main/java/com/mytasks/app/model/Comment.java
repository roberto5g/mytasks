package com.mytasks.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User creator;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
