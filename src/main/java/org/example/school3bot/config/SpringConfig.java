package org.example.school3bot.config;

import org.example.school3bot.telegram.SchoolBot;
import org.example.school3bot.telegram.handler.CallbackQueryHandler;
import org.example.school3bot.telegram.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final TelegramConfig telegramConfig;

    @Autowired
    public SpringConfig(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }

    @Bean
    public SchoolBot springSchoolBot(CallbackQueryHandler callbackQueryHandler, MessageHandler messageHandler) {
        SchoolBot schoolBot = new SchoolBot(messageHandler, callbackQueryHandler);

        schoolBot.setBotPath(telegramConfig.getWebHookPath());
        schoolBot.setBotUsername(telegramConfig.getBotName());
        schoolBot.setBotToken(telegramConfig.getBotToken());

        return schoolBot;
    }
}
