package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.Objects;

public class TestFee {
    private Long id;
    private Short type;
    private Integer amount;
    private Long testId;
    private Timestamp date;
    private Short step;

    public TestFee() {}

    public TestFee(Long id, Short type, Integer amount, Long testId, Timestamp date, Short step) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.testId = testId;
        this.date = date;
        this.step = step;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Short getStep() {
        return step;
    }

    public void setStep(Short step) {
        this.step = step;
    }

    public boolean isEqualTo(TestFee fee) {
        return id.equals(fee.getId()) && type.equals(fee.getType()) && amount.equals(fee.getAmount()) && testId.equals(fee.getTestId()) && step == null ? fee.getStep() == null : step.equals(fee.getStep());
    }

    @Override
    public String toString() {
        return "TestFee{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", testId=" + testId +
                ", date=" + date +
                ", step=" + step +
                '}';
    }
}
