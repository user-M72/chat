package com.example.WebSocket.repository;

import com.example.WebSocket.entity.Status;
import com.example.WebSocket.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByStatus(Status status);
}
