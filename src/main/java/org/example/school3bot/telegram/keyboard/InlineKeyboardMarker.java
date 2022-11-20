package org.example.school3bot.telegram.keyboard;

import org.example.school3bot.constant.Bell;
import org.example.school3bot.constant.Day;
import org.example.school3bot.constant.Letter;
import org.example.school3bot.constant.Parallel;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineKeyboardMarker {

    public static InlineKeyboardMarkup getDay() {

        List<String> week = new ArrayList<>();

        for (Day day : Day.values())
            week.add(day.getDayOfWeek());

        return getInlineKeyboard(week);
    }

    public static InlineKeyboardMarkup getParallel() {

        List<String> parallels = new ArrayList<>();

        for (Parallel parallel : Parallel.values())
            parallels.add(parallel.getParallel());

        return getInlineKeyboard(parallels);
    }

    public static InlineKeyboardMarkup getLetter() {
        List<String> letters = new ArrayList<>();

        for (Letter letter : Letter.values())
            letters.add(letter.getLetter());

        return getInlineKeyboard(letters);
    }
    public static InlineKeyboardMarkup getTimetable() {
        List<String> bells = new ArrayList<>();
        for (Bell bell : Bell.values())
            bells.add(bell.getValue());

        return getInlineKeyboard(bells);
    }

    private static InlineKeyboardMarkup getInlineKeyboard(List<String> nameButton) {

        List<List<InlineKeyboardButton>> allRows = new ArrayList<>();

        for(String name : nameButton)  {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(name);
            button.setCallbackData(name);

            allRows.add(new ArrayList<>(Collections.singletonList(button)));
        }

        return new InlineKeyboardMarkup(allRows);
    }
}
