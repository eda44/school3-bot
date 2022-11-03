package org.example.school3bot.telegram.handler;

import org.example.school3bot.constant.Answer;
import org.example.school3bot.telegram.keyboard.InlineKeyboardMarker;
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
            String chatId = message.getChatId().toString();
            String textMsg = message.getText();
            SendMessage answer;

            switch (textMsg) {
                case "/start" -> {
                    answer = new SendMessage(chatId, "Мои почтение, " + message.getChat().getFirstName());
                    answer.setReplyMarkup(replyKeyboardMarker.getMainMenu());
                    return answer;
                }
                case "Расписание уроков" -> {
                    answer = new SendMessage(chatId, Answer.CHOOSE_DAY);
                    answer.setReplyMarkup(InlineKeyboardMarker.getDay());
                    return answer;
                }
                case "Расписание звонков" -> {
                    answer = new SendMessage(chatId, "Полноценные али сокращённые уроки?");
                    answer.setReplyMarkup(InlineKeyboardMarker.getTimetable());
                    return answer;
                }
            }
        }

        return new SendMessage(message.getChatId().toString(), "Извини, приятель, но такой команды не существует");
    }
}
