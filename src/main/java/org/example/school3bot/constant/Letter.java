package org.example.school3bot.constant;

public enum Letter {
    A("А"),
    B("Б");

    public static final String CALLBACK_DATA = "LETTER";
    final String letter;

    Letter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
