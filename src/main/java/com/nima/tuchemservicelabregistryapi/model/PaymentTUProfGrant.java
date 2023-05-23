package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class PaymentTUProfGrant extends Payment {
    private Byte verificationStatus;
    private Long professorId;

    public PaymentTUProfGrant() {}

    public PaymentTUProfGrant(Long id, Timestamp date, Long amount, Long serviceId, Byte verificationStatus, Long professorId) {
        super(id, date, amount, serviceId);
        this.verificationStatus = verificationStatus;
        this.professorId = professorId;
    }

    public Byte getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Byte verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}
