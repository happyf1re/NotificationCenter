package com.muravlev.core.services;

import com.muravlev.core.entities.TelegramToken;
import com.muravlev.core.entities.User;
import com.muravlev.core.mappers.UserMapper;
import com.muravlev.core.repositories.TelegramTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TelegramTokenService {

    @Autowired
    private TelegramTokenRepository telegramTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public String generateNewTokenForUser(Long userId) {
        User user = userMapper.convertToEntity(userService.getUserById(userId));
        String token = UUID.randomUUID().toString();

        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setUser(user);
        telegramToken.setToken(token);
        telegramToken.setCreationDate(LocalDateTime.now());

        telegramTokenRepository.save(telegramToken);
        return token;
    }

    public Optional<User> getUserByToken(String token) {
        return telegramTokenRepository.findByToken(token)
                .map(TelegramToken::getUser);
    }

    public void linkUserToTelegramChat(Long userId, Long chatId) {
        User user = userMapper.convertToEntity(userService.getUserById(userId));
        user.setTelegramChatId(chatId);
        userService.saveUser(user);
    }

    public void cleanOldTokens() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24); // удаляем токены старше 24 часов
        telegramTokenRepository.deleteByCreationDateBefore(threshold);
    }
}
