package com.mytasks.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private StatusTask status = StatusTask.WAITING;
    private Date dueDate;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
