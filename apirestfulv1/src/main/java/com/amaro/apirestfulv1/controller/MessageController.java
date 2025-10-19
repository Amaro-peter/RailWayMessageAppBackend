package com.amaro.apirestfulv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amaro.apirestfulv1.model.Message;
import com.amaro.apirestfulv1.service.MessageService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://192.168.15.15:5173", "http://localhost:5173", "http://127.0.0.1:5173", "https://vercel-message-app-front-end.vercel.app/"})
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String sender, 
                                        @RequestParam String receiver, 
                                        @RequestParam String content) {
        try {
            Message message = messageService.sendMessage(sender, receiver, content);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try {
            Message sentMessage = messageService.sendMessage(message);
            return ResponseEntity.ok(sentMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(@RequestParam String user1,
                                                         @RequestParam String user2) {
        List<Message> conversation = messageService.getConversationBetweenUsers(user1, user2);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/received/{receiver}")
    public ResponseEntity<List<Message>> getReceivedMessages(@PathVariable String receiver) {
        List<Message> messages = messageService.getReceivedMessages(receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/{sender}")
    public ResponseEntity<List<Message>> getSentMessages(@PathVariable String sender) {
        List<Message> messages = messageService.getSentMessages(sender);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/recent/{username}")
    public ResponseEntity<List<Message>> getRecentMessages(@PathVariable String username,
                                                           @RequestParam(defaultValue = "50") int limit) {
        List<Message> messages = messageService.getRecentMessagesForUser(username, limit);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/partners/{username}")
    public ResponseEntity<List<String>> getConversationPartners(@PathVariable String username) {
        List<String> partners = messageService.getConversationPartners(username);
        return ResponseEntity.ok(partners);
    }
}