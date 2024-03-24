package ru.polainam.utils;

import java.time.Duration;

/**
 * Утилитный класс для парсинга и форматирования длительности звонка.
 */
public class Parser {

    /**
     * Форматирует длительность звонка в виде строки "чч:мм:сс".
     *
     * @param duration Длительность звонка в объекте Duration.
     * @return Строковое представление длительности звонка.
     */
    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Парсит строку в формате "чч:мм:сс" в объект Duration.
     *
     * @param time Строка с длительностью звонка.
     * @return Объект Duration, представляющий длительность звонка.
     */
    public static Duration parseDuration(String time) {
        String[] parts = time.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
}
