package com.example.WebSocket.service;

import com.example.WebSocket.entity.Status;
import com.example.WebSocket.entity.User;
import com.example.WebSocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user) {
        var storedUser = userRepository.findById(user.getNickname()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectedUser(){
        return userRepository.findAllByStatus(Status.ONLINE);
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }
}
