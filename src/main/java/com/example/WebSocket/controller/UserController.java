package com.example.WebSocket.controller;

import com.example.WebSocket.entity.User;
import com.example.WebSocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired private UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/topic/public")
    public User addUser(@Payload User user){
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/topic/public")
    public User disconnect(@Payload User user){
        userService.disconnect(user);
        return user;
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> findConnectUser(){
        return ResponseEntity.ok(userService.findAllUser());
    }

}
