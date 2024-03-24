package ru.polainam.utils;

import java.util.Map;

public class Printer {
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
