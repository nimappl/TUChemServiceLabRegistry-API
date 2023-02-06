package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class PaymentTUProfGrant extends Payment {
    private byte verificationStatus;
    private long professorId;

    public PaymentTUProfGrant() {}

    public PaymentTUProfGrant(long id, Timestamp date, double amount, long serviceId, byte verificationStatus, long professorId) {
        super(id, date, amount, serviceId);
        this.verificationStatus = verificationStatus;
        this.professorId = professorId;
    }

    public byte getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(byte verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }
}
