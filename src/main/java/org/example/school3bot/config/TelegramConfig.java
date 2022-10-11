package org.example.school3bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TelegramConfig {

    @Value("${telegram.webhook-path}")
    private String webHookPath;

    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String botToken;

    public String getWebHookPath() {
        return webHookPath;
    }

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}
