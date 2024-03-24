package ru.polainam;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.polainam.models.UDR;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.polainam.utils.Parser.parseDuration;
import static ru.polainam.utils.Printer.printReport;

/**
 * Класс для генерации отчетов о длительности звонков.
 */
@Component
public class ReportGenerator {
    private final ObjectMapper objectMapper;
    private static final int CHARGING_PERIOD = 12;

    /**
     * Конструктор класса ReportGenerator.
     *
     * @param objectMapper ObjectMapper для преобразования JSON.
     */
    @Autowired
    public ReportGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Генерирует общий отчет о длительности звонков для всех абонентов за каждый месяц.
     */
    public void generateReport() {
        Map<String, String> totalCallTimes = new HashMap<>();
        for (int month = 1; month <= CHARGING_PERIOD; month++) {
            File monthDirectory = new File("reports/" + month);
            if (!monthDirectory.exists()) {
                continue;
            }
            for (File file : Objects.requireNonNull(monthDirectory.listFiles())) {
                try {
                    UDR udr = objectMapper.readValue(file, UDR.class);
                    String msisdn = udr.getMsisdn();
                    String incomingCallTime = udr.getIncomingCall().getTotalTime() != null ? udr.getIncomingCall().getTotalTime() : "00:00:00";
                    String outcomingCallTime = udr.getOutcomingCall().getTotalTime() != null ? udr.getOutcomingCall().getTotalTime() : "00:00:00";
                    String totalTime = sumCallTimes(incomingCallTime, outcomingCallTime);
                    String totalNewTime = sumCallTimes(totalCallTimes.getOrDefault(msisdn, "00:00:00"), totalTime);
                    totalCallTimes.put(msisdn, totalNewTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        printReport(totalCallTimes);
    }

    /**
     * Генерирует отчет о длительности звонков для конкретного абонента за каждый месяц.
     *
     * @param msisdn Номер абонента.
     */
    public void generateReport(String msisdn) {
        Map<String, String> totalCallTimes = new HashMap<>();
        for (int month = 1; month <= CHARGING_PERIOD; month++) {
            File monthDirectory = new File("reports/" + month);
            if (!monthDirectory.exists()) {
                continue;
            }
            for (File file : Objects.requireNonNull(monthDirectory.listFiles())) {
                try {
                    UDR udr = objectMapper.readValue(file, UDR.class);
                    if (!udr.getMsisdn().equals(msisdn)) {
                        continue;
                    }
                    String incomingCallTime = udr.getIncomingCall().getTotalTime() != null ? udr.getIncomingCall().getTotalTime() : "00:00:00";
                    String outcomingCallTime = udr.getOutcomingCall().getTotalTime() != null ? udr.getOutcomingCall().getTotalTime() : "00:00:00";
                    String totalTime = sumCallTimes(incomingCallTime, outcomingCallTime);
                    totalCallTimes.put(String.valueOf(month), totalTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        printReport(msisdn, totalCallTimes);
    }

    /**
     * Генерирует отчет о длительности звонков для конкретного абонента за указанный месяц.
     *
     * @param msisdn Номер абонента.
     * @param month  Номер месяца.
     */
    public void generateReport(String msisdn, int month) {
        Map<String, String> totalCallTimes = new HashMap<>();
        File monthDirectory = new File("reports/" + month);
        if (monthDirectory.exists()) {
            try {
                File file = new File("reports/" + month + "/" + msisdn + "_" + month + ".json");
                if (file.exists()) {
                    UDR udr = objectMapper.readValue(file, UDR.class);
                    String incomingCallTime = udr.getIncomingCall().getTotalTime() != null ? udr.getIncomingCall().getTotalTime() : "00:00:00";
                    String outcomingCallTime = udr.getOutcomingCall().getTotalTime() != null ? udr.getOutcomingCall().getTotalTime() : "00:00:00";
                    String totalTime = sumCallTimes(incomingCallTime, outcomingCallTime);
                    totalCallTimes.put(msisdn, totalTime);
                } else {
                    System.out.println("No report found for MSISDN: " + msisdn + " in month: " + month);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No reports found for month: " + month);
            return;
        }
        printReport(msisdn, month, totalCallTimes);
    }

    /**
     * Суммирует временные интервалы звонков.
     *
     * @param time1 Время первого звонка в формате HH:mm:ss.
     * @param time2 Время второго звонка в формате HH:mm:ss.
     * @return Сумма временных интервалов звонков в формате HH:mm:ss.
     */
    private String sumCallTimes(String time1, String time2) {
        Duration duration1 = parseDuration(time1);
        Duration duration2 = parseDuration(time2);
        Duration totalDuration = duration1.plus(duration2);
        long hours = totalDuration.toHours();
        long minutes = totalDuration.toMinutesPart();
        long seconds = totalDuration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
