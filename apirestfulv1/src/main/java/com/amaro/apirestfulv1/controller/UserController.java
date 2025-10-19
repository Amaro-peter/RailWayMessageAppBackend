package com.amaro.apirestfulv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amaro.apirestfulv1.model.PublicKeyRequest;
import com.amaro.apirestfulv1.model.PublicKeyResponse;
import com.amaro.apirestfulv1.model.User;
import com.amaro.apirestfulv1.service.UserService;
import com.amaro.apirestfulv1.service.MessageService;
import com.amaro.apirestfulv1.service.PublicKeyService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://192.168.15.15:5173", "http://localhost:5173", "http://127.0.0.1:5173", "https://vercel-message-app-front-end.vercel.app/"})
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PublicKeyService publicKeyService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String username) {
        List<User> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}/conversation-partners")
    public ResponseEntity<List<String>> getConversationPartners(@PathVariable String username) {
        List<String> partners = messageService.getConversationPartners(username);
        return ResponseEntity.ok(partners);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<Boolean> userExists(@PathVariable String username) {
        boolean exists = userService.userExists(username);
        return ResponseEntity.ok(exists);
    }

    // Endpoint para verificar se o usuário possui chave pública

    @PostMapping("/public-key")
    public ResponseEntity<?> uploadPublicKey(@RequestBody PublicKeyRequest request) {
        try {
            PublicKeyResponse response = publicKeyService.saveOrUpdatePublicKey(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/public-key/{userId}")
    public ResponseEntity<?> getPublicKey(@PathVariable String userId) {
        try {
            PublicKeyResponse response = publicKeyService.getPublicKey(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public-key/{userId}/exists")
    public ResponseEntity<Boolean> hasPublicKey(@PathVariable String userId) {
        boolean exists = publicKeyService.hasPublicKey(userId);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/public-key/{userId}")
    public ResponseEntity<?> deletePublicKey(@PathVariable String userId) {
        try {
            publicKeyService.deletePublicKey(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/public-key/exchange/{requestingUserId}/{targetUserId}")
    public ResponseEntity<?> exchangePublicKeys(
            @PathVariable String requestingUserId,
            @PathVariable String targetUserId) {
        try {
            PublicKeyResponse response = publicKeyService.exchangePublicKeys(requestingUserId, targetUserId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}