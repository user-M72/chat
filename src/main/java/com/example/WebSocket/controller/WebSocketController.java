package com.example.WebSocket.controller;

import com.example.WebSocket.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // В памяти храним активные чаты: chatId -> сообщения
    private final ConcurrentHashMap<String, String> activeChats = new ConcurrentHashMap<>();

    // Генерация chatId на основе nickname
    private String generateChatId(String user1, String user2){
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }

    @MessageMapping("/chat")
    public void sendMessage(@Payload ChatMessage message){
        // создаём chatId для пары пользователей
        String chatId = generateChatId(message.getSenderId(), message.getRecipientId());

        // сохраняем последнее сообщение в оперативной памяти (опционально)
        activeChats.put(chatId, message.getContent());

        // отправка получателю
        messagingTemplate.convertAndSendToUser(
                message.getRecipientId(),
                "/queue/messages",
                message
        );

        // отправка отправителю (чтобы он видел своё сообщение)
        messagingTemplate.convertAndSendToUser(
                message.getSenderId(),
                "/queue/messages",
                message
        );
    }
}
