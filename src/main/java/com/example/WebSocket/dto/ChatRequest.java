package com.example.WebSocket.dto;

public record ChatRequest(
        String senderId,
        String recipientId
) {
}
