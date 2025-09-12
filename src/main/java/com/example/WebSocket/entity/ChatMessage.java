package com.example.WebSocket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity(name = "chat_message")
@Getter
@Setter
@Builder
public class ChatMessage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
    private String timestamp;
}
