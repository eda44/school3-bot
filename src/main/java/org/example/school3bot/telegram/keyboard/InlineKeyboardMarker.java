package org.example.school3bot.telegram.keyboard;

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

        return getInlineKeyboard(week, Day.CALLBACK_DATA);
    }

    public static InlineKeyboardMarkup getParallel() {

        List<String> parallels = new ArrayList<>();

        for (Parallel parallel : Parallel.values())
            parallels.add(parallel.getParallel());

        return getInlineKeyboard(parallels, Parallel.CALLBACK_DATA);
    }

    public static InlineKeyboardMarkup getLetter() {
        List<String> letters = new ArrayList<>();

        for (Letter letter : Letter.values())
            letters.add(letter.getLetter());

        return getInlineKeyboard(letters, Letter.CALLBACK_DATA);
    }
    private static InlineKeyboardMarkup getInlineKeyboard(List<String> nameButton, String callbackData) {

        List<List<InlineKeyboardButton>> allRows = new ArrayList<>();

        for(String name : nameButton)  {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(name);
            button.setCallbackData(name);

            allRows.add(new ArrayList<>(Collections.singletonList(button)));
        }

        return new InlineKeyboardMarkup(allRows);
    }

    public static InlineKeyboardMarkup getTimetable() {
        InlineKeyboardMarkup timeTable = new InlineKeyboardMarkup();

        InlineKeyboardButton longLessons = new InlineKeyboardButton();
        longLessons.setText("Полноценные");
        longLessons.setCallbackData("full");

        InlineKeyboardButton shortLessons = new InlineKeyboardButton();
        shortLessons.setText("Сокращенные");
        shortLessons.setCallbackData("short");

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(longLessons);
        firstRow.add(shortLessons);

        List<List<InlineKeyboardButton>> allRows = new ArrayList<>();
        allRows.add(firstRow);

        timeTable.setKeyboard(allRows);

        return timeTable;
    }
}
