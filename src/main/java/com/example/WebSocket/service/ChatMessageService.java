package com.example.WebSocket.service;

import com.example.WebSocket.entity.ChatMessage;
import com.example.WebSocket.entity.StatusMessage;
import com.example.WebSocket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private ChatRoomService chatRoomService;
    @Autowired private MongoOperations mongoOperations;

    public ChatMessage save(ChatMessage chatMessage){
        chatMessage.setStatusMessage(StatusMessage.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessage(String senderId, String recipientId){
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
        var messages = chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());
        if (messages.size() > 0){
            updateStatuses(senderId, recipientId, StatusMessage.DELIVERED);
        }
        return messages;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatusMessage(
                senderId, recipientId, StatusMessage.RECEIVED);
    }

    public ChatMessage findById(String id){
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatusMessage(StatusMessage.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                }).orElseThrow(() -> new RuntimeException("can`t find message (" + id + ")"));
    }

    private void updateStatuses(String senderId, String recipientId, StatusMessage statusMessage) {
        Query query = new Query(
                Criteria.where("senderId").is(senderId)
                        .and("recipientId").is(recipientId)
        );

        Update update = Update.update("status", statusMessage);

        mongoOperations.updateMulti(query, update, ChatMessage.class);
    }
}
