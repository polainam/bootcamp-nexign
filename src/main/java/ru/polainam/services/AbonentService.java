package ru.polainam.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polainam.models.Abonent;
import ru.polainam.repositories.AbonentRepository;

import java.util.List;

/**
 * Сервис для работы с абонентами.
 */
@Service
@Transactional(readOnly = true)
public class AbonentService {

    private final AbonentRepository abonentRepository;

    public AbonentService(AbonentRepository abonentRepository) {
        this.abonentRepository = abonentRepository;
    }

    /**
     * Получает список всех абонентов.
     *
     * @return Список всех абонентов.
     */
    public List<Abonent> findAll() {
        return abonentRepository.findAll();
    }
}
