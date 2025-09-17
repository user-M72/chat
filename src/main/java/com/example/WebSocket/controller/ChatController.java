package com.example.WebSocket.controller;

import com.example.WebSocket.dto.MessageDto;
import com.example.WebSocket.entity.ChatMessage;

import com.example.WebSocket.entity.ChatNotification;
import com.example.WebSocket.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public void processMessage(@Payload ChatMessage chatMessage){
        ChatMessage saveMsg = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        saveMsg.getId(),
                        saveMsg.getSenderId(),
                        saveMsg.getRecipientId(),
                        saveMsg.getContent()
                )
        );
    }

    @GetMapping("/message/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessage(@PathVariable String senderId, @PathVariable String recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, recipientId));
    }

    @MessageMapping("/chat/{senderId}/{recipientId}")
    @SendTo("/topic/chat/{senderId}/{recipientId}")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage,
            @DestinationVariable String senderId,
            @DestinationVariable String recipientId
    ){
        ChatMessage saved = chatMessageService.save(chatMessage);
        return saved;
    }
}
