package org.example.school3bot.telegram.handler;

import org.example.school3bot.telegram.keyboard.ReplyKeyboardMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler {

    private final ReplyKeyboardMarker replyKeyboardMarker;

    @Autowired
    public MessageHandler(ReplyKeyboardMarker replyKeyboardMarker) {
        this.replyKeyboardMarker = replyKeyboardMarker;
    }

    public BotApiMethod<?> processMessageAnswer(Message message) {
        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                SendMessage answer = new SendMessage
                        (message.getChatId().toString(), "Мои почтение, " + message.getChat().getFirstName());
                answer.setReplyMarkup(replyKeyboardMarker.getMainMenu());
                return answer;
            }
        }

        return new SendMessage(message.getChatId().toString(), "Извини, приятель, но такой команды не существует");
    }
}
