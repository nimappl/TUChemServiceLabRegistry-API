package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private Long id;
    private Timestamp date;
    private Integer sampleQuantity;
    private Integer testTime;
    private Long price;
    private Byte hasSamplePrep;
    private Test test;
    private TestFee testFee;
    private List<TestPrep> testPrep;
    private LabPersonnel servingPersonnel;
    private Customer customer;
    private Byte settlementStatus;
    private List<ServiceResultFile> resultFiles;
    private List<Discount> discounts;
    private List<Payment> payments;
    private String considerations;

    public Service() {}

    public Service(Long id, Timestamp date, Integer sampleQuantity, Integer testTime, Long price, Byte hasSamplePrep, Test test, TestFee testFee, List<TestPrep> testPrep, LabPersonnel servingPersonnel, Customer customer, Byte settlementStatus, List<ServiceResultFile> resultFiles, List<Discount> discounts, List<Payment> payments, String considerations) {
        this.id = id;
        this.date = date;
        this.sampleQuantity = sampleQuantity;
        this.testTime = testTime;
        this.price = price;
        this.hasSamplePrep = hasSamplePrep;
        this.test = test;
        this.testFee = testFee;
        this.testPrep = testPrep;
        this.servingPersonnel = servingPersonnel;
        this.customer = customer;
        this.settlementStatus = settlementStatus;
        this.resultFiles = resultFiles;
        this.discounts = discounts;
        this.payments = payments;
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

    public Byte getHasSamplePrep() {
        return hasSamplePrep;
    }

    public void setHasSamplePrep(Byte hasSamplePrep) {
        this.hasSamplePrep = hasSamplePrep;
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

    public List<TestPrep> getTestPrep() {
        return testPrep;
    }

    public void setTestPrep(List<TestPrep> testPrep) {
        this.testPrep = testPrep;
    }

    public void setTestFee(TestFee testFee) {
        this.testFee = testFee;
    }

    public LabPersonnel getServingPersonnel() {
        return servingPersonnel;
    }

    public void setServingPersonnel(LabPersonnel servingPersonnel) {
        this.servingPersonnel = servingPersonnel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Byte getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Byte settlementStatus) {
        this.settlementStatus = settlementStatus;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getConsiderations() {
        return considerations;
    }

    public void setConsiderations(String considerations) {
        this.considerations = considerations;
    }
}
