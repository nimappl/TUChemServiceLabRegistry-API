package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Date;

public class TPayment {
    private Long id;
    private Short type;
    private Date date;
    private Long amount;
    private String customerName;

    public TPayment() {}

    public TPayment(Long id, Short type, Date date, Long amount, String customerName) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "TPayment{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", amount=" + amount +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
