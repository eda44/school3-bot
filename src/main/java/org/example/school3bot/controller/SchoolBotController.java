package org.example.school3bot.controller;

import org.example.school3bot.telegram.SchoolBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class SchoolBotController {

    private final SchoolBot schoolBot;

    @Autowired
    public SchoolBotController(SchoolBot schoolBot) {
        this.schoolBot = schoolBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return schoolBot.onWebhookUpdateReceived(update);
    }
}
