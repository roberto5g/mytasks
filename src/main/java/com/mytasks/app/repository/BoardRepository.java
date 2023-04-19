package com.mytasks.app.repository;

import com.mytasks.app.model.Board;
import com.mytasks.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByOwnerId(Long id);
}
