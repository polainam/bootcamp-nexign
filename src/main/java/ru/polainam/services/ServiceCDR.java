package ru.polainam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.polainam.models.Abonent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.List;
import java.util.Random;

@Component
public class ServiceCDR {

    private final AbonentService abonentService;
    private static final int YEAR = 2024;

    @Autowired
    public ServiceCDR(AbonentService abonentService) {
        this.abonentService = abonentService;
    }

    public void generateCDR() {
        Random random = new Random();
        try {
            Path directory = Paths.get("cdr_2024");
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            for (int month = 1; month <= 12; month++) {
                int numberOfRecords = random.nextInt(51) + 50; // Генерация случайного количества записей в месяце (от 50 до 100)
                String filename = String.format("cdr_%d/%d.txt", YEAR, month);
                FileWriter writer = new FileWriter(filename);
                for (int i = 0; i < numberOfRecords; i++) {
                    List<Abonent> abonents = abonentService.findAll();
                    Abonent abonent = abonents.get(random.nextInt(abonents.size()));
                    int callType = random.nextInt(2) + 1;
                    Instant startDate = generateRandomDate(YEAR, month); // Генерация случайной даты начала звонка
                    Instant endDate = startDate.plusSeconds(random.nextInt(3600)); // Длительность звонка - случайное количество секунд (до 1 часа)
                    writer.write(callType + "," + abonent.getNumber() + "," + startDate.getEpochSecond() + "," + endDate.getEpochSecond() + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Instant generateRandomDate(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = startEpochDay + (long) (Math.random() * (endEpochDay - startEpochDay));

        // Получаем случайный момент времени внутри дня
        LocalDateTime randomDateTime = LocalDateTime.ofEpochSecond(randomDay * 24 * 60 * 60, 0, ZoneOffset.UTC)
                .plusSeconds((long) (Math.random() * 24 * 60 * 60));

        return randomDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

}
