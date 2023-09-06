package com.muravlev.core.repositories;

import com.muravlev.core.entities.UserTelegram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTelegramRepository extends JpaRepository<UserTelegram, Long> {
    Optional<UserTelegram> findByUserId(Long userId);
}
