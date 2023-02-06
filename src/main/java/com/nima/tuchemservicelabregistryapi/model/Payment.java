package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class Payment {
    private long id;
    private Timestamp date;
    private double amount;
    private long serviceId;

    public Payment() {}

    public Payment(long id, Timestamp date, double amount, long serviceId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.serviceId = serviceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }
}
