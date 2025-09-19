package com.example.WebSocket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotification{

    private String id;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;

}
