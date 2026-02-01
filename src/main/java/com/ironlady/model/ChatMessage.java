package com.ironlady.model;

import java.sql.Timestamp;

public class ChatMessage {
    private int id;
    private String sender; // "user" or "assistant"
    private String message;
    private Timestamp createdAt;

    public ChatMessage() {}

    public ChatMessage(int id, String sender, String message, Timestamp createdAt) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
