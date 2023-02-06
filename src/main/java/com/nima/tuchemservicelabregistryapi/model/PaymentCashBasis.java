package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class PaymentCashBasis extends Payment {
    private short type;
    private String trackingNo;

    public PaymentCashBasis() {}

    public PaymentCashBasis(long id, Timestamp date, double amount, long serviceId, short type, String trackingNo) {
        super(id, date, amount, serviceId);
        this.type = type;
        this.trackingNo = trackingNo;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
}
