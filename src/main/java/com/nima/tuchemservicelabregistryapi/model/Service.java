package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private Long id;
    private Timestamp date;
    private Integer sampleQuantity;
    private Integer testTime;
    private Long price;
    private Test test;
    private TestFee testFee;
    private List<TestPrep> testPreps;
    private LabPersonnel servingPersonnel;
    private Account customer;
    private Long customerId;
    private List<ServiceResultFile> resultFiles;
    private List<Discount> discounts;
    private String considerations;

    public Service() {}

    public Service(Long id, Timestamp date, Integer sampleQuantity, Integer testTime, Long price, Test test, TestFee testFee, List<TestPrep> testPreps, LabPersonnel servingPersonnel, Account customer, Long customerId, List<ServiceResultFile> resultFiles, List<Discount> discounts, String considerations) {
        this.id = id;
        this.date = date;
        this.sampleQuantity = sampleQuantity;
        this.testTime = testTime;
        this.price = price;
        this.test = test;
        this.testFee = testFee;
        this.testPreps = testPreps;
        this.servingPersonnel = servingPersonnel;
        this.customer = customer;
        this.customerId = customerId;
        this.resultFiles = resultFiles;
        this.discounts = discounts;
        this.considerations = considerations;
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

    public Integer getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(Integer sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public Integer getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public TestFee getTestFee() {
        return testFee;
    }

    public void setTestFee(TestFee testFee) {
        this.testFee = testFee;
    }

    public List<TestPrep> getTestPreps() {
        return testPreps;
    }

    public void setTestPreps(List<TestPrep> testPreps) {
        this.testPreps = testPreps;
    }

    public LabPersonnel getServingPersonnel() {
        return servingPersonnel;
    }

    public void setServingPersonnel(LabPersonnel servingPersonnel) {
        this.servingPersonnel = servingPersonnel;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ServiceResultFile> getResultFiles() {
        return resultFiles;
    }

    public void setResultFiles(List<ServiceResultFile> resultFiles) {
        this.resultFiles = resultFiles;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public String getConsiderations() {
        return considerations;
    }

    public void setConsiderations(String considerations) {
        this.considerations = considerations;
    }
}
