package com.muravlev.core.repositories;

import com.muravlev.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    boolean existsByName(String name);
    User findByName(String name);

    boolean existsByEmail(String email);
    Optional<User> findByTelegramChatId(Long telegramChatId);
}

