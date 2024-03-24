package ru.polainam.utils;

import java.util.Map;

/**
 * Утилитный класс для печати отчетов о длительности звонков.
 */
public class Printer {
    /**
     * Печатает отчет о длительности звонков для всех абонентов.
     *
     * @param totalCallTimes Словарь с общей длительностью звонков для каждого абонента.
     */
    public static void printReport(Map<String, String> totalCallTimes) {
        int tableWidth = 37;
        String dashLine = "-".repeat(tableWidth);
        System.out.println(dashLine);
        System.out.printf("| %-15s | %-15s |\n", "Number", "Total Call Time");
        System.out.println(dashLine);
        for (Map.Entry<String, String> entry : totalCallTimes.entrySet()) {
            System.out.printf("| %-15s | %-15s |\n", entry.getKey(), entry.getValue());
        }
        System.out.println(dashLine);
    }

    /**
     * Печатает отчет о длительности звонков для указанного абонента.
     *
     * @param msisdn         Номер абонента.
     * @param totalCallTimes Словарь с общей длительностью звонков для каждого месяца.
     */
    public static void printReport(String msisdn, Map<String, String> totalCallTimes) {
        int tableWidth = 37;
        String dashLine = "-".repeat(tableWidth);
        System.out.println("Report for MSISDN: " + msisdn);
        System.out.println(dashLine);
        System.out.printf("| %-15s | %-15s |\n", "Month", "Total Call Time");
        System.out.println(dashLine);
        for (Map.Entry<String, String> entry : totalCallTimes.entrySet()) {
            System.out.printf("| %-15s | %-15s |\n", entry.getKey(), entry.getValue());
        }
        System.out.println(dashLine);
    }

    /**
     * Печатает отчет о длительности звонков для указанного абонента в указанном месяце.
     *
     * @param msisdn         Номер абонента.
     * @param month          Номер месяца.
     * @param totalCallTimes Словарь с общей длительностью звонков для каждого абонента в указанном месяце.
     */
    public static void printReport(String msisdn, int month, Map<String, String> totalCallTimes) {
        int tableWidth = 37;
        String dashLine = "-".repeat(tableWidth);
        System.out.println("Report for MSISDN: " + msisdn + " in month: " + month);
        System.out.println(dashLine);
        System.out.printf("| %-15s | %-15s |\n", "Month", "Total Call Time");
        System.out.println(dashLine);
        System.out.printf("| %-15s | %-15s |\n", month, totalCallTimes.get(msisdn));
        System.out.println(dashLine);
    }
}
