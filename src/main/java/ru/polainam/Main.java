package ru.polainam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.polainam.services.ServiceCDR;
import ru.polainam.services.ServiceUDR;

@SpringBootApplication
public class Main {
    private final ServiceCDR serviceCDR;
    private final ServiceUDR serviceUDR;
    private final ReportGenerator reportGenerator;

    @Autowired
    public Main(ServiceCDR serviceCDR, ServiceUDR serviceUDR, ReportGenerator reportGenerator) {
        this.serviceCDR = serviceCDR;
        this.serviceUDR = serviceUDR;
        this.reportGenerator = reportGenerator;
    }

    public static void main(String[] args ) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    public void run() {
        serviceCDR.generateCDR();
        serviceUDR.generateUDR();
        reportGenerator.generateReport();
    }
}
