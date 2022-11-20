package org.example.school3bot.telegram.handler;

import org.example.school3bot.constant.Text;
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

            if(textMsg.equals(Text.START_MESSAGE.getValue()))
            {
                answer = new SendMessage(chatId, Text.GREETING.getValue() + message.getChat().getFirstName());
                answer.setReplyMarkup(replyKeyboardMarker.getMainMenu());
                return answer;
            }
            else if(textMsg.equals(Text.LESSONS_SCHEDULE.getValue()))
            {
                answer = new SendMessage(chatId, Text.CHOOSE_DAY.getValue());
                answer.setReplyMarkup(InlineKeyboardMarker.getDay());
                return answer;
            }
            else if(textMsg.equals(Text.BELLS_SCHEDULE.getValue()))
            {
                answer = new SendMessage(chatId, Text.CHOOSE_BELLS_SCHEDULE.getValue());
                answer.setReplyMarkup(InlineKeyboardMarker.getTimetable());
                return answer;
            }
        }

        return new SendMessage(message.getChatId().toString(), Text.ERROR.getValue());
    }
}
