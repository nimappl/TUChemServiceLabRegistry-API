package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class TestFee {
    private long id;
    private short type;
    private double amount;
    private long testId;
    private Timestamp date;
    private short step;

    public TestFee() {}

    public TestFee(long id, short type, double amount, long testId, Timestamp date, short step) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.testId = testId;
        this.date = date;
        this.step = step;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public short getStep() {
        return step;
    }

    public void setStep(short step) {
        this.step = step;
    }
}
