package org.example.school3bot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Text {

    CHOOSE_DAY("Выберете день недели"),
    CHOOSE_PARALLEL("Параллель, параллель, параллелечка моя ..."),
    CHOOSE_LETTER("Скажете букву - скажу расписание, не иначе!"),
    LESSONS_SCHEDULE("Расписание уроков"),
    BELLS_SCHEDULE("Расписание звонков"),
    START_MESSAGE("/start"),
    GREETING("Мои почтение, "),
    CHOOSE_BELLS_SCHEDULE("Длинные или короткие уроки?"),
    ERROR("Извини, приятель, но такой команды не существует"),
    CLASS_LESSON("Классный час");

    private final String value;
}
