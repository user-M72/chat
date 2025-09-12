package com.example.WebSocket.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chat_room")
public class ChatRoom{
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;

}
