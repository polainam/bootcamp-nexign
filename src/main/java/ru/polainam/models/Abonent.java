package ru.polainam.models;

import jakarta.persistence.*;

/**
 * Модель абонента, представляющая собой сущность "Abonent" в базе данных.
 */
@Entity
@Table(name = "abonents")
public class Abonent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "number")
    private String number;

    /**
     * Получить полное имя абонента.
     *
     * @return Полное имя абонента.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Установить полное имя абонента.
     *
     * @param fullName Полное имя абонента.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Получить номер абонента.
     *
     * @return Номер абонента.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Установить номер абонента.
     *
     * @param number Номер абонента.
     */
    public void setNumber(String number) {
        this.number = number;
    }
}
