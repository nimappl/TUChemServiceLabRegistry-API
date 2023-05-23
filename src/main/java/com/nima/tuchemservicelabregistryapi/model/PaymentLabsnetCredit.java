package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class PaymentLabsnetCredit extends Payment {
    private String creditTitle;
    private String transactionCode;

    public PaymentLabsnetCredit() {}

    public PaymentLabsnetCredit(Long id, Timestamp date, Long amount, Long serviceId, String creditTitle, String transactionCode) {
        super(id, date, amount, serviceId);
        this.creditTitle = creditTitle;
        this.transactionCode = transactionCode;
    }

    public String getCreditTitle() {
        return creditTitle;
    }

    public void setCreditTitle(String creditTitle) {
        this.creditTitle = creditTitle;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
