package com.example.WebSocket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotification{

    private Long id;
    private String senderId;
    private String recipientId;
    private String content;
}
