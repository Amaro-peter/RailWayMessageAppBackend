package com.amaro.apirestfulv1.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Entity
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String receiver;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime timestamp;

    private Boolean encrypted = false;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "encryptedContent", column = @Column(name = "encrypted_content", columnDefinition = "LONGTEXT")),
        @AttributeOverride(name = "encryptedSymmetricKey", column = @Column(name = "encrypted_symmetric_key", columnDefinition = "TEXT")),
        @AttributeOverride(name = "nonce", column = @Column(name = "encryption_nonce", length = 255)),
        @AttributeOverride(name = "algorithm", column = @Column(name = "encryption_algorithm", length = 100)),
        @AttributeOverride(name = "timestamp", column = @Column(name = "encryption_timestamp", length = 50))
    })
    private EncryptedMessage encryptedData;

    // ...existing code...
    public Message(String sender, String receiver, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
        this.encrypted = false;
    }

    public Message(String sender, String receiver, String content, LocalDateTime timestamp, Boolean encrypted, EncryptedMessage encryptedData) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
        this.encrypted = encrypted;
        this.encryptedData = encryptedData;
    }

    // ...existing code...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public EncryptedMessage getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(EncryptedMessage encryptedData) {
        this.encryptedData = encryptedData;
    }
}