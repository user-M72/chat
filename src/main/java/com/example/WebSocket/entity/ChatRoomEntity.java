package com.example.WebSocket.entity;

import com.example.WebSocket.entity.base.BaseDomain;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "chats")
@Getter
@Setter
public class ChatRoomEntity extends BaseDomain<UUID> {

    private String chatId;
    private String senderId;
    private String recepientId;
}
