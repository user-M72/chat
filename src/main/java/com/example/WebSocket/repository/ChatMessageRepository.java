package com.example.WebSocket.repository;

import com.example.WebSocket.entity.ChatMessage;
import com.example.WebSocket.entity.StatusMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatId(String chatId);

    long countBySenderIdAndRecipientIdAndStatusMessage(String senderId, String recipientId, StatusMessage statusMessage);
}
