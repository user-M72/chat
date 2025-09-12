package com.example.WebSocket.repository;

import com.example.WebSocket.entity.Status;
import com.example.WebSocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByStatus(Status status);
    Optional<User> findByNickname(String nickname);
}
