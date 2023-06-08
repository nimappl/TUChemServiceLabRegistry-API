package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Date;

public class TService {
    private Long id;
    private String testName;
    private Integer sampleQuantity;
    private String customerName;
    private Date date;
    private Long totalPrice;

    public TService() {}

    public TService(Long id, String testName, Integer sampleQuantity, String customerName, Date date, Long totalPrice) {
        this.id = id;
        this.testName = testName;
        this.sampleQuantity = sampleQuantity;
        this.customerName = customerName;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
