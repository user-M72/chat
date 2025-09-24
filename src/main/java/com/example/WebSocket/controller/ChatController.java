package com.example.WebSocket.controller;

import com.example.WebSocket.entity.ChatMessage;
import com.example.WebSocket.entity.ChatNotification;
import com.example.WebSocket.service.ChatMessageService;
import com.example.WebSocket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage){

        var chatId = chatRoomService.getChatId(

                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true);

        chatMessage.setChatId(chatId.get());

        ChatMessage saveMsg = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        saveMsg.getId(),
                        saveMsg.getSenderId(),
                        saveMsg.getRecipientId(),
                        saveMsg.getContent(),
                        saveMsg.getTimestamp()
                )
        );
    }

    @GetMapping("/message/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessage(@PathVariable String senderId,
                                                             @PathVariable String recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessage(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }

}
