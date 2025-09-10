package com.example.WebSocket.service;

import com.example.WebSocket.entity.Status;
import com.example.WebSocket.entity.UserEntity;
import com.example.WebSocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(UserEntity user){
        var storedUser = userRepository.findById(user.getNickname()).orElse(null);
        if (storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<UserEntity> findConnectedUser(){
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
