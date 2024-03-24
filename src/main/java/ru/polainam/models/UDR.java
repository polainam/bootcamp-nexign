package ru.polainam.models;

public class UDR {
   private String msisdn;
    Call incomingCall;
    Call outcomingCall;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Call getIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(Call incomingCall) {
        this.incomingCall = incomingCall;
    }

    public Call getOutcomingCall() {
        return outcomingCall;
    }

    public void setOutcomingCall(Call outcomingCall) {
        this.outcomingCall = outcomingCall;
    }
}
