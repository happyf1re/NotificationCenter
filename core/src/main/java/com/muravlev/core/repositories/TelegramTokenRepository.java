package com.muravlev.core.repositories;

import com.muravlev.core.entities.TelegramToken;
import com.muravlev.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TelegramTokenRepository extends JpaRepository<TelegramToken, Long> {

    Optional<TelegramToken> findByToken(String token);

    Optional<TelegramToken> findByUser(User user);

    void deleteByCreationDateBefore(LocalDateTime timeThreshold);
}
