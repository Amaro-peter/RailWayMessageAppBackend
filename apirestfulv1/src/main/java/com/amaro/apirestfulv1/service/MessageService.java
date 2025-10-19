package com.amaro.apirestfulv1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.apirestfulv1.model.EncryptedMessage;
import com.amaro.apirestfulv1.model.Message;
import com.amaro.apirestfulv1.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public Message sendMessage(String sender, String receiver, String content) {
        // Validate that both users exist
        if (!userService.userExists(sender)) {
            throw new RuntimeException("Sender user does not exist: " + sender);
        }
        if (!userService.userExists(receiver)) {
            throw new RuntimeException("Receiver user does not exist: " + receiver);
        }
        
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setEncrypted(false);
        return messageRepository.save(message);
    }

    public Message sendMessage(Message message) {
        // Validate that both users exist
        if (!userService.userExists(message.getSender())) {
            throw new RuntimeException("Sender user does not exist: " + message.getSender());
        }
        if (!userService.userExists(message.getReceiver())) {
            throw new RuntimeException("Receiver user does not exist: " + message.getReceiver());
        }

        // Validate encrypted message data
        if (message.getEncrypted() != null && message.getEncrypted() && message.getEncryptedData() == null) {
            throw new RuntimeException("Encrypted message must contain encrypted data");
        }
        
        // Validate encrypted data sizes
        if (message.getEncrypted() != null && message.getEncrypted() && message.getEncryptedData() != null) {
            EncryptedMessage encData = message.getEncryptedData();
            if (encData.getEncryptedSymmetricKey() != null && encData.getEncryptedSymmetricKey().length() > 65535) {
                throw new RuntimeException("Encrypted symmetric key is too large");
            }
            if (encData.getEncryptedContent() != null && encData.getEncryptedContent().length() > 16777215) {
                throw new RuntimeException("Encrypted content is too large");
            }
        }
        
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        
        // Set default value for encrypted if null
        if (message.getEncrypted() == null) {
            message.setEncrypted(false);
        }
        
        return messageRepository.save(message);
    }

    // ...existing code...
    public List<Message> getConversationBetweenUsers(String user1, String user2) {
        return messageRepository.findConversationBetweenUsers(user1, user2);
    }

    public List<Message> getReceivedMessages(String receiver) {
        return messageRepository.findByReceiver(receiver);
    }

    public List<Message> getSentMessages(String sender) {
        return messageRepository.findBySender(sender);
    }

    public List<Message> getMessagesBetweenUsers(String sender, String receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public List<Message> getRecentMessagesForUser(String username, int limit) {
        List<Message> allMessages = messageRepository.findAllMessagesForUser(username);
        return allMessages.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<String> getConversationPartners(String username) {
        List<Message> sentMessages = messageRepository.findBySender(username);
        List<Message> receivedMessages = messageRepository.findByReceiver(username);
        
        return sentMessages.stream()
                .map(Message::getReceiver)
                .distinct()
                .collect(Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        list.addAll(receivedMessages.stream()
                                .map(Message::getSender)
                                .distinct()
                                .filter(sender -> !sender.equals(username))
                                .collect(Collectors.toList()));
                        return list.stream().distinct().collect(Collectors.toList());
                    }
                ));
    }
}
