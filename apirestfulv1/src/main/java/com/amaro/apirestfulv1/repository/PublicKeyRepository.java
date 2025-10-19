package com.amaro.apirestfulv1.repository;

import com.amaro.apirestfulv1.model.PublicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKey, Long> {
    
    Optional<PublicKey> findByUserId(String userId);
    
    boolean existsByUserId(String userId);
    
    void deleteByUserId(String userId);
}
