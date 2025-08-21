package com.example.WebSocket.controller;

import com.example.WebSocket.model.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public MessageDto sendMessage(@Payload MessageDto message){
        return new MessageDto(message.from(), message.text().trim());
    }
}
