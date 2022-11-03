package org.example.school3bot.telegram.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.school3bot.constant.Answer;
import org.example.school3bot.constant.Day;
import org.example.school3bot.constant.Letter;
import org.example.school3bot.constant.Parallel;
import org.example.school3bot.telegram.keyboard.InlineKeyboardMarker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class CallbackQueryHandler {
    private final Map<String, String> request = new HashMap<>();

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        SendMessage answer = null;

        if(Arrays.stream(Day.values()).anyMatch(s -> s.getDayOfWeek().equals(data)))
        {
            request.put(Day.CALLBACK_DATA, data);
            answer = new SendMessage(chatId, Answer.CHOOSE_PARALLEL);
            answer.setReplyMarkup(InlineKeyboardMarker.getParallel());
        }
        else if(request.containsKey(Day.CALLBACK_DATA))
        {
            if (Arrays.stream(Parallel.values()).anyMatch(s -> s.getParallel().equals(data)))
            {
                request.put(Parallel.CALLBACK_DATA, data);
                answer = new SendMessage(chatId, Answer.CHOOSE_LETTER);
                answer.setReplyMarkup(InlineKeyboardMarker.getLetter());
            }

            if(request.containsKey(Parallel.CALLBACK_DATA)) {

                if (Arrays.stream(Letter.values()).anyMatch(s -> s.getLetter().equals(data))) {
                    request.put(Letter.CALLBACK_DATA, data);
                    request.forEach((k, v) -> System.out.println(k + " : " + v));
                    answer = new SendMessage(chatId, parseTable());
                }
            }
        }else {
            answer = new SendMessage(chatId, Answer.CHOOSE_DAY);
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
            wb = new XSSFWorkbook(new FileInputStream("C:/Users/danil/IdeaProjects/timetable.xlsx"));
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

        for (int i = 1; i < 9; i++) {
            Row row1 = sheet.getRow(i);
            Cell cell = row1.getCell(index);

            if (cell.getStringCellValue().isEmpty()) break;

            result.append(i).append(". ").append(cell.getStringCellValue()).append("\n");
        }
        return result.toString();
    }
}
