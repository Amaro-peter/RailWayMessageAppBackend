package com.amaro.apirestfulv1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Embeddable
public class EncryptedMessage {
    private String encryptedContent;
    private String encryptedSymmetricKey; // KEM ciphertext do Kyber
    private String nonce; // IV/nonce para AES-GCM
    private String algorithm;
    private String timestamp;

    // Construtor completo
    public EncryptedMessage(String encryptedContent, String encryptedSymmetricKey, String nonce, String algorithm) {
        this.encryptedContent = encryptedContent;
        this.encryptedSymmetricKey = encryptedSymmetricKey;
        this.nonce = nonce;
        this.algorithm = algorithm;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Getters e Setters
    public String getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public String getEncryptedSymmetricKey() {
        return encryptedSymmetricKey;
    }

    public void setEncryptedSymmetricKey(String encryptedSymmetricKey) {
        this.encryptedSymmetricKey = encryptedSymmetricKey;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}