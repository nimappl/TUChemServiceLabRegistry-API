package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private Long id;
    private Timestamp date;
    private Integer sampleQuantity;
    private Integer testTime;
    private Long additionalCosts;
    private Long totalPrice;
    private Long testId;
    private Test test;
    private Long testFeeId;
    private TestFee testFee;
    private List<TestPrep> testPreps;
    private Long servingPersonnelId;
    private LabPersonnel servingPersonnel;
    private Long customerAccountId;
    private Account customerAccount;
    private List<ServiceResultFile> resultFiles;
    private List<Discount> discounts;
    private String considerations;

    public Service() {}

    public Service(Long id, Timestamp date, Integer sampleQuantity, Integer testTime, Long additionalCosts, Long totalPrice, Long testId, Test test, Long testFeeId, TestFee testFee, List<TestPrep> testPreps, Long servingPersonnelId, LabPersonnel servingPersonnel, Long customerAccountId, Account customerAccount, List<ServiceResultFile> resultFiles, List<Discount> discounts, String considerations) {
        this.id = id;
        this.date = date;
        this.sampleQuantity = sampleQuantity;
        this.testTime = testTime;
        this.additionalCosts = additionalCosts;
        this.totalPrice = totalPrice;
        this.testId = testId;
        this.test = test;
        this.testFeeId = testFeeId;
        this.testFee = testFee;
        this.testPreps = testPreps;
        this.servingPersonnelId = servingPersonnelId;
        this.servingPersonnel = servingPersonnel;
        this.customerAccountId = customerAccountId;
        this.customerAccount = customerAccount;
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

    public Long getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(Long additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Long getTestFeeId() {
        return testFeeId;
    }

    public void setTestFeeId(Long testFeeId) {
        this.testFeeId = testFeeId;
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

    public Long getServingPersonnelId() {
        return servingPersonnelId;
    }

    public void setServingPersonnelId(Long servingPersonnelId) {
        this.servingPersonnelId = servingPersonnelId;
    }

    public LabPersonnel getServingPersonnel() {
        return servingPersonnel;
    }

    public void setServingPersonnel(LabPersonnel servingPersonnel) {
        this.servingPersonnel = servingPersonnel;
    }

    public Long getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(Long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public Account getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(Account customerAccount) {
        this.customerAccount = customerAccount;
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
