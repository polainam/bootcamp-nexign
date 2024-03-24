package ru.polainam.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.polainam.models.Call;
import ru.polainam.models.UDR;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static ru.polainam.utils.Parser.formatDuration;
import static ru.polainam.utils.Parser.parseDuration;

/**
 * Класс для генерации UDR (Usage Detail Record).
 */
@Component
public class ServiceUDR {
    private final ObjectMapper objectMapper;

    @Autowired
    public ServiceUDR(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Генерирует UDR файлы для заданного периода времени.
     *
     * @param sdrDirectory    Директория, содержащая CDR файлы.
     * @param reportDirectory Директория, в которую будут сохранены UDR файлы.
     * @param chargingPeriod  Период, за который генерируются UDR файлы.
     */
    public void generateUDR(String sdrDirectory, String reportDirectory, int chargingPeriod) {
        for (int month = 1; month <= chargingPeriod; month++) {
            List<UDR> udrList = new ArrayList<>();
            File cdrDirectory = new File(sdrDirectory);
            for (File cdrFile : Objects.requireNonNull(cdrDirectory.listFiles())) {
                if (cdrFile.getName().startsWith(month + ".txt")) {
                    processCDRFile(cdrFile, udrList);
                }
            }
            createUDRFiles(udrList, month, reportDirectory);
        }
    }

    private void processCDRFile(File cdrFile, List<UDR> udrList) {
        try (Scanner scanner = new Scanner(cdrFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String msisdn = parts[1];
                String callType = parts[0];
                Instant start = Instant.ofEpochSecond(Long.parseLong(parts[2]));
                Instant end = Instant.ofEpochSecond(Long.parseLong(parts[3]));

                UDR udr = findUDRByMsisdn(udrList, msisdn);
                if (udr == null) {
                    udr = new UDR();
                    udr.setMsisdn(msisdn);
                    udr.setIncomingCall(new Call());
                    udr.setOutcomingCall(new Call());
                    udrList.add(udr);
                }

                Duration duration = Duration.between(start, end);
                if (callType.equals("01")) {
                    updateTotalTime(udr.getOutcomingCall(), duration);
                } else if (callType.equals("02")) {
                    updateTotalTime(udr.getIncomingCall(), duration);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private UDR findUDRByMsisdn(List<UDR> udrList, String msisdn) {
        for (UDR udr : udrList) {
            if (udr.getMsisdn().equals(msisdn)) {
                return udr;
            }
        }
        return null;
    }

    private void updateTotalTime(Call call, Duration duration) {
        if (call.getTotalTime() == null) {
            call.setTotalTime(formatDuration(duration));
        } else {
            Duration currentDuration = parseDuration(call.getTotalTime());
            Duration totalDuration = currentDuration.plus(duration);
            call.setTotalTime(formatDuration(totalDuration));
        }
    }

    private void createUDRFiles(List<UDR> udrList, int month, String reportDirectory) {
        String monthDirectory = String.format("%s/%d", reportDirectory, month);
        File directory = new File(monthDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (UDR udr : udrList) {
            String fileName = String.format("%s/%s_%d.json", monthDirectory, udr.getMsisdn(), month);
            try (FileWriter writer = new FileWriter(fileName)) {
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(writer, udr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
