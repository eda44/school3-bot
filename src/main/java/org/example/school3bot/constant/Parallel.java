package org.example.school3bot.constant;

public enum Parallel {
    TENTH("10"),
    ELEVENTH("11");

    public static final String CALLBACK_DATA = "PARALLEL";
    final String parallel;

    Parallel(String parallel) {
        this.parallel = parallel;
    }

    public String getParallel() {
        return parallel;
    }


}
