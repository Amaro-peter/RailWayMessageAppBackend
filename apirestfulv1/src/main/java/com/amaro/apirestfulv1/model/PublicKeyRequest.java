package com.amaro.apirestfulv1.model;

public class PublicKeyRequest {
    
    private String userId;
    private String publicKey;
    private String algorithm;

    public PublicKeyRequest() {}

    public PublicKeyRequest(String userId, String publicKey, String algorithm) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.algorithm = algorithm;
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
}
