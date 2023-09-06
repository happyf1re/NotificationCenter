package com.muravlev.core.services;

import com.muravlev.core.entities.User;
import com.muravlev.core.entities.UserTelegram;
import com.muravlev.core.repositories.UserRepository;
import com.muravlev.core.repositories.UserTelegramRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final UserTelegramRepository userTelegramRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public TelegramBotService(UserTelegramRepository userTelegramRepository, UserRepository userRepository, UserService userService) {
        this.userTelegramRepository = userTelegramRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedMessage = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            // Пытаемся преобразовать полученное сообщение в ID пользователя (Long)
            try {
                Long userId = Long.parseLong(receivedMessage);
                Optional<User> optionalUser = userService.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setTelegramChatId(chatId);
                    userService.saveUser(user);
                    sendMessage(chatId, "Вы успешно связали ваш аккаунт с этим Telegram-ботом!");
                } else {
                    sendMessage(chatId, "Не удалось найти вашего пользователя в системе. Пожалуйста, удостоверьтесь, что вы отправили правильный ID.");
                }
            } catch (NumberFormatException e) {
                sendMessage(chatId, "Пожалуйста, отправьте только ваш ID пользователя.");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
