package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private long id;
    private Timestamp date;
    private int sampleQuantity;
    private int testTime;
    private double price;
    private byte hasSamplePrep;
    private Test test;
    private TestFee testFee;
    private LabPersonnel servingPersonnel;
    private Customer customer;
    private byte settlementStatus;
    private List<ServiceResultFile> resultFiles;
    private List<Discount> discounts;
    private List<Payment> payments;
    private String considerations;

    public Service() {}

    public Service(long id, Timestamp date, int sampleQuantity, int testTime, double price, byte hasSamplePrep, Test test, TestFee testFee, LabPersonnel servingPersonnel, Customer customer, byte settlementStatus, List<ServiceResultFile> resultFiles, List<Discount> discounts, List<Payment> payments, String considerations) {
        this.id = id;
        this.date = date;
        this.sampleQuantity = sampleQuantity;
        this.testTime = testTime;
        this.price = price;
        this.hasSamplePrep = hasSamplePrep;
        this.test = test;
        this.testFee = testFee;
        this.servingPersonnel = servingPersonnel;
        this.customer = customer;
        this.settlementStatus = settlementStatus;
        this.resultFiles = resultFiles;
        this.discounts = discounts;
        this.payments = payments;
        this.considerations = considerations;
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

    public int getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(int sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte getHasSamplePrep() {
        return hasSamplePrep;
    }

    public void setHasSamplePrep(byte hasSamplePrep) {
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

    public byte getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(byte settlementStatus) {
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
