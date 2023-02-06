package com.nima.tuchemservicelabregistryapi.model;

public class TestPrep {
    private long id;
    private long testId;
    private String type;
    private double price;

    public TestPrep() {}

    public TestPrep(long id, long testId, String type, double price) {
        this.id = id;
        this.testId = testId;
        this.type = type;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
