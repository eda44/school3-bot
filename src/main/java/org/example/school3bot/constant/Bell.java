package org.example.school3bot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bell {
    FULL("Длинные уроки"),
    SHORT("Короткие уроки");

    private final String value;

}
