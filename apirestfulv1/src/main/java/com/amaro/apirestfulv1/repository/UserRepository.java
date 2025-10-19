package com.amaro.apirestfulv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.amaro.apirestfulv1.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String username);
    boolean existsByUsername(String username);
}