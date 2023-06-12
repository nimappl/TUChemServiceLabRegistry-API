package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class Payment {
    private Long id;
    private Timestamp date;
    private Long amount;
    private Long accountId;
    private String labsnetCreditTitle;
    private String labsnetTransactionCode;
    private Short cashBasisType;
    private String cashBasisTrackingNo;
    private Long grantProfessorId;

    public Payment() {}

    public Payment(Long id, Timestamp date, Long amount, Long accountId, String labsnetCreditTitle, String labsnetTransactionCode, Short cashBasisType, String cashBasisTrackingNo, Long grantProfessorId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.accountId = accountId;
        this.labsnetCreditTitle = labsnetCreditTitle;
        this.labsnetTransactionCode = labsnetTransactionCode;
        this.cashBasisType = cashBasisType;
        this.cashBasisTrackingNo = cashBasisTrackingNo;
        this.grantProfessorId = grantProfessorId;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getLabsnetCreditTitle() {
        return labsnetCreditTitle;
    }

    public void setLabsnetCreditTitle(String labsnetCreditTitle) {
        this.labsnetCreditTitle = labsnetCreditTitle;
    }

    public String getLabsnetTransactionCode() {
        return labsnetTransactionCode;
    }

    public void setLabsnetTransactionCode(String labsnetTransactionCode) {
        this.labsnetTransactionCode = labsnetTransactionCode;
    }

    public Short getCashBasisType() {
        return cashBasisType;
    }

    public void setCashBasisType(Short cashBasisType) {
        this.cashBasisType = cashBasisType;
    }

    public String getCashBasisTrackingNo() {
        return cashBasisTrackingNo;
    }

    public void setCashBasisTrackingNo(String cashBasisTrackingNo) {
        this.cashBasisTrackingNo = cashBasisTrackingNo;
    }

    public Long getGrantProfessorId() {
        return grantProfessorId;
    }

    public void setGrantProfessorId(Long grantProfessorId) {
        this.grantProfessorId = grantProfessorId;
    }
}
