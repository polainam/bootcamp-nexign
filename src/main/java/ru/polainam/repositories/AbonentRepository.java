package ru.polainam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.polainam.models.Abonent;

@Repository
public interface AbonentRepository extends JpaRepository<Abonent, Integer> {

}
