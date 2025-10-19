package com.amaro.apirestfulv1.repository;

import com.amaro.apirestfulv1.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findBySenderAndReceiver(String sender, String receiver);
    
    List<Message> findBySender(String sender);
    
    List<Message> findByReceiver(String receiver);
    
    @Query("SELECT m FROM Message m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1) ORDER BY m.timestamp ASC")
    List<Message> findConversationBetweenUsers(@Param("user1") String user1, @Param("user2") String user2);
    
    @Query("SELECT m FROM Message m WHERE m.sender = :username OR m.receiver = :username ORDER BY m.timestamp DESC")
    List<Message> findAllMessagesForUser(@Param("username") String username);
}