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

@Component
public class ReportGenerator {
    private final ObjectMapper objectMapper;
    private static final int CHARGING_PERIOD = 12;
    @Autowired
    public ReportGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
