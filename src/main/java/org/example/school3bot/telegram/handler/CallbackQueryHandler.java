package org.example.school3bot.telegram.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.school3bot.constant.*;
import org.example.school3bot.telegram.keyboard.InlineKeyboardMarker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class CallbackQueryHandler {

    @Value("${table_path}")
    private String path;
    private final Map<String, String> request = new HashMap<>();

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        SendMessage answer = null;

        if(Arrays.stream(Day.values()).anyMatch(s -> s.getDayOfWeek().equals(data)))
        {
            request.put(Day.CALLBACK_DATA, data);
            answer = new SendMessage(chatId, Text.CHOOSE_PARALLEL.getValue());
            answer.setReplyMarkup(InlineKeyboardMarker.getParallel());
        }
        else if(request.containsKey(Day.CALLBACK_DATA))
        {
            if (Arrays.stream(Parallel.values()).anyMatch(s -> s.getParallel().equals(data)))
            {
                request.put(Parallel.CALLBACK_DATA, data);
                answer = new SendMessage(chatId, Text.CHOOSE_LETTER.getValue());
                answer.setReplyMarkup(InlineKeyboardMarker.getLetter());
            }
            else if(request.containsKey(Parallel.CALLBACK_DATA))
            {

                if (Arrays.stream(Letter.values()).anyMatch(s -> s.getLetter().equals(data))) {
                    request.put(Letter.CALLBACK_DATA, data);
                    request.forEach((k, v) -> System.out.println(k + " : " + v));
                    answer = new SendMessage(chatId, parseTable());
                }
            }
            else
            {
                answer = new SendMessage(chatId, Text.CHOOSE_PARALLEL.getValue());
                answer.setReplyMarkup(InlineKeyboardMarker.getParallel());
            }
        }/*
        else if (Arrays.stream(Bell.values()).anyMatch(s -> s.getValue().equals(data))) {
            List<String> bells;
            StringBuilder result = new StringBuilder();
            result.append(data).append("\n\n");
            int counter = 0;

            if(data.equals(Bell.FULL.getValue())) {
                bells = getBellSchedule(Day.TUESDAY.getDayOfWeek());
                for (String bell : bells) {
                    if(!bell.isEmpty())
                        result.append(++counter).append(" урок. ").append(bell).append("\n");
                }
            }else {
                bells = getBellSchedule(Day.MONDAY.getDayOfWeek());
                for (String bell : bells) {
                    if (counter == 0) {
                        result.append(Text.CLASS_LESSON.getValue()).append("\n");
                        counter++;
                        continue;
                    }
                    result.append(counter++).append(" урок. ").append(bell).append("\n");
                }
            }

            answer = new SendMessage(chatId, result.toString());
        }*/
        else
        {
            answer = new SendMessage(chatId, Text.CHOOSE_DAY.getValue());
            answer.setReplyMarkup(InlineKeyboardMarker.getDay());
        }
        return answer;
    }

    private String parseTable() {
        StringBuilder result = new StringBuilder();
        String chosenDay = request.get(Day.CALLBACK_DATA),
                chosenParallel = request.get(Parallel.CALLBACK_DATA),
                chosenLetter = request.get(Letter.CALLBACK_DATA);

        result.append(chosenDay).append(" - ").append(chosenParallel).append(chosenLetter).append("\n\n");

        Workbook wb;
        try {
            wb = new XSSFWorkbook(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = wb.getSheet(chosenDay);

        Row classRow = sheet.getRow(0);
        Iterator<Cell> cellClassIter = classRow.cellIterator();

        int index = 0;
        while (cellClassIter.hasNext()) {
            Cell cell = cellClassIter.next();
            if (cell.getStringCellValue().equals(chosenParallel + chosenLetter)){
                index = cell.getColumnIndex();
                break;
            }
        }

        List<String> lessons = new ArrayList<>();
        List<String> windows = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(index);

            if(cell.getStringCellValue().isEmpty()){
                windows.add("\uD83E\uDE9F");
            }else {
                if(!windows.isEmpty()){
                    lessons.addAll(windows);
                    windows.clear();
                }
                lessons.add(cell.getStringCellValue());
            }
        }

        List<String> bellSchedule = getBellSchedule(chosenDay);

        int counter = 0;

        if(chosenDay.equals(Day.MONDAY.getDayOfWeek()))
            result.append("Разговоры о важном\uD83E\uDD14 ").append(bellSchedule.get(counter)).append("\n");

        if(chosenDay.equals(Day.FRIDAY.getDayOfWeek()))
            result.append("Классный час\uD83D\uDE34 ").append(bellSchedule.get(counter)).append("\n");

        for (String lesson : lessons) {
            String timeLesson = bellSchedule.get(counter);
            result.append(++counter).append(". ").append(lesson).append("\s").append(timeLesson).append("\n");
        }
        return result.toString();
    }

    private List<String> getBellSchedule(String chosenDay){
        List<String> bellSchedule = new ArrayList<>();

        Workbook wb;
        try {
            wb = new XSSFWorkbook(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = wb.getSheet(Text.BELLS_SCHEDULE.getValue());

        int index = chosenDay.equals(Day.MONDAY.getDayOfWeek()) || chosenDay.equals(Day.FRIDAY.getDayOfWeek()) ? 1 : 0;

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(index);
            bellSchedule.add(cell.getStringCellValue());
        }

        return  bellSchedule;
    }

}
