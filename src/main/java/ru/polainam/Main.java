package ru.polainam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.polainam.services.ServiceCDR;

@SpringBootApplication
public class Main implements CommandLineRunner {

    ServiceCDR serviceCDR;

    @Autowired
    public Main(ServiceCDR serviceCDR) {
        this.serviceCDR = serviceCDR;
    }

    public static void main(String[] args ) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        serviceCDR.generateCDR();
    }
}
