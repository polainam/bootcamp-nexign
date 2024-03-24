package ru.polainam.models;

/**
 * Класс UDR (Usage Data Record), представляющий собой запись о пользовательских данных.
 * Содержит информацию о номере телефона (msisdn) и двух звонках: входящем и исходящем.
 */
public class UDR {
    private String msisdn;
    Call incomingCall;
    Call outcomingCall;

    /**
     * Получить номер телефона (MSISDN).
     *
     * @return Номер телефона (MSISDN).
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Установить номер телефона (MSISDN).
     *
     * @param msisdn Номер телефона (MSISDN).
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * Получить объект звонка, представляющий входящий звонок.
     *
     * @return Объект звонка, представляющий входящий звонок.
     */
    public Call getIncomingCall() {
        return incomingCall;
    }

    /**
     * Установить объект звонка, представляющий входящий звонок.
     *
     * @param incomingCall Объект звонка, представляющий входящий звонок.
     */
    public void setIncomingCall(Call incomingCall) {
        this.incomingCall = incomingCall;
    }

    /**
     * Получить объект звонка, представляющий исходящий звонок.
     *
     * @return Объект звонка, представляющий исходящий звонок.
     */
    public Call getOutcomingCall() {
        return outcomingCall;
    }

    /**
     * Установить объект звонка, представляющий исходящий звонок.
     *
     * @param outcomingCall Объект звонка, представляющий исходящий звонок.
     */
    public void setOutcomingCall(Call outcomingCall) {
        this.outcomingCall = outcomingCall;
    }
}
