package ru.polainam.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polainam.models.Abonent;
import ru.polainam.repositories.AbonentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceCDR {

    private final AbonentRepository abonentRepository;

    public ServiceCDR(AbonentRepository abonentRepository) {
        this.abonentRepository = abonentRepository;
    }

    public List<Abonent> findAll() {
        return abonentRepository.findAll();
    }

    public static void generateCDR() {

    }
}
