package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class Payment {
    private Long id;
    private Timestamp date;
    private Long amount;
    private Long serviceId;

    public Payment() {}

    public Payment(Long id, Timestamp date, Long amount, Long serviceId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.serviceId = serviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
