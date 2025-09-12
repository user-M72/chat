package com.example.WebSocket.repository;

import com.example.WebSocket.entity.Status;
import com.example.WebSocket.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByStatus(Status status);
}
