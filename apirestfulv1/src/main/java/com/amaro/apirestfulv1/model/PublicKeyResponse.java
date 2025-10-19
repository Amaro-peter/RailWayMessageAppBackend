package com.amaro.apirestfulv1.model;

import java.time.LocalDateTime;

public class PublicKeyResponse {
    
    private String userId;
    private String publicKey;
    private String algorithm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PublicKeyResponse() {}

    public PublicKeyResponse(String userId, String publicKey, String algorithm, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.algorithm = algorithm;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PublicKeyResponse fromEntity(PublicKey publicKey) {
        return new PublicKeyResponse(
            publicKey.getUserId(),
            publicKey.getPublicKey(),
            publicKey.getAlgorithm(),
            publicKey.getCreatedAt(),
            publicKey.getUpdatedAt()
        );
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}