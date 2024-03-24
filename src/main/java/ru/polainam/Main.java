package ru.polainam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.polainam.services.ServiceCDR;
import ru.polainam.services.ServiceUDR;

/**
 * Главный класс приложения, отвечающий за запуск и инициализацию.
 */
@SpringBootApplication
public class Main {
    private final ServiceCDR serviceCDR;
    private final ServiceUDR serviceUDR;
    private final ReportGenerator reportGenerator;
    private static final int CHARGING_PERIOD = 12;
    private static final String CDR_DIRECTORY = "cdr_2024";
    public static final String REPORTS_DIRECTORY = "reports";

    /**
     * Конструктор класса Main.
     *
     * @param serviceCDR      Сервис для генерации CDR файлов.
     * @param serviceUDR      Сервис для генерации UDR файлов.
     * @param reportGenerator Генератор отчетов.
     */
    @Autowired
    public Main(ServiceCDR serviceCDR, ServiceUDR serviceUDR, ReportGenerator reportGenerator) {
        this.serviceCDR = serviceCDR;
        this.serviceUDR = serviceUDR;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Метод main, который запускает приложение.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * Метод, выполняющий основную логику приложения.
     * Здесь генерируются CDR и UDR файлы, а также отчеты о длительности звонков.
     */
    @Autowired
    public void run() {
        serviceCDR.generateCDR(CHARGING_PERIOD);
        serviceUDR.generateUDR(CDR_DIRECTORY, REPORTS_DIRECTORY, CHARGING_PERIOD);
        reportGenerator.generateReport();
//        reportGenerator.generateReport("79011234567");
//        reportGenerator.generateReport("79011234567", 7);
    }
}
