package ru.polainam.models;

/**
 * Класс звонка, содержащий общее время звонка.
 */
public class Call {

    private String totalTime;

    /**
     * Получить общее время звонка.
     *
     * @return Общее время звонка.
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * Установить общее время звонка.
     *
     * @param totalTime Общее время звонка.
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
