package com.example.WebSocket.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "chat_message")
public class ChatMessage{
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
    private String timestamp;

    public void setStatus(StatusMessage received) {
    }
}
