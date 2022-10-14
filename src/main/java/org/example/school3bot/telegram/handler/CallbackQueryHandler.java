package org.example.school3bot.telegram.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }
}
