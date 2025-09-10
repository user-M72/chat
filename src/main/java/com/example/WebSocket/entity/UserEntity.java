package com.example.WebSocket.entity;

import com.example.WebSocket.entity.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity extends BaseDomain<UUID> {

    private String nickname;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Status status;
}
