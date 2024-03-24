package ru.polainam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.polainam.services.ServiceCDR;
import ru.polainam.services.ServiceUDR;

@SpringBootApplication
public class Main implements CommandLineRunner {

    ServiceCDR serviceCDR;
    ServiceUDR serviceUDR;

    @Autowired
    public Main(ServiceCDR serviceCDR, ServiceUDR serviceUDR) {
        this.serviceCDR = serviceCDR;
        this.serviceUDR = serviceUDR;
    }

    public static void main(String[] args ) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        serviceCDR.generateCDR();
        serviceUDR.generateUDR();
    }
}
