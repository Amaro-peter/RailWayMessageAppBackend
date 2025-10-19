package com.amaro.apirestfulv1.service;

import com.amaro.apirestfulv1.model.PublicKey;
import com.amaro.apirestfulv1.model.PublicKeyRequest;
import com.amaro.apirestfulv1.model.PublicKeyResponse;
import com.amaro.apirestfulv1.repository.PublicKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PublicKeyService {

    @Autowired
    private PublicKeyRepository publicKeyRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public PublicKeyResponse saveOrUpdatePublicKey(PublicKeyRequest request) {
        // Validar se o usuário existe
        if (!userService.userExists(request.getUserId())) {
            throw new RuntimeException("User does not exist: " + request.getUserId());
        }

        // Validar dados obrigatórios
        if (request.getPublicKey() == null || request.getPublicKey().trim().isEmpty()) {
            throw new RuntimeException("Public key cannot be null or empty");
        }

        if (request.getAlgorithm() == null || request.getAlgorithm().trim().isEmpty()) {
            throw new RuntimeException("Algorithm cannot be null or empty");
        }

        Optional<PublicKey> existingKey = publicKeyRepository.findByUserId(request.getUserId());
        
        PublicKey publicKey;
        if (existingKey.isPresent()) {
            // Atualizar chave existente
            publicKey = existingKey.get();
            publicKey.setPublicKey(request.getPublicKey());
            publicKey.setAlgorithm(request.getAlgorithm());
        } else {
            // Criar nova chave
            publicKey = new PublicKey(
                request.getUserId(),
                request.getPublicKey(),
                request.getAlgorithm()
            );
        }

        PublicKey savedKey = publicKeyRepository.save(publicKey);
        return PublicKeyResponse.fromEntity(savedKey);
    }

    public PublicKeyResponse getPublicKey(String userId) {
        Optional<PublicKey> publicKey = publicKeyRepository.findByUserId(userId);
        
        if (publicKey.isEmpty()) {
            throw new RuntimeException("Public key not found for user: " + userId);
        }

        return PublicKeyResponse.fromEntity(publicKey.get());
    }

    public boolean hasPublicKey(String userId) {
        return publicKeyRepository.existsByUserId(userId);
    }

    @Transactional
    public void deletePublicKey(String userId) {
        if (!publicKeyRepository.existsByUserId(userId)) {
            throw new RuntimeException("Public key not found for user: " + userId);
        }
        
        publicKeyRepository.deleteByUserId(userId);
    }

    public PublicKeyResponse exchangePublicKeys(String requestingUserId, String targetUserId) {
        // Validar se ambos os usuários existem
        if (!userService.userExists(requestingUserId)) {
            throw new RuntimeException("Requesting user does not exist: " + requestingUserId);
        }
        
        if (!userService.userExists(targetUserId)) {
            throw new RuntimeException("Target user does not exist: " + targetUserId);
        }

        // Retornar a chave pública do usuário alvo
        return getPublicKey(targetUserId);
    }
}