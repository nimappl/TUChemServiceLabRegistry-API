package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class PaymentCashBasis extends Payment {
    private Short type;
    private String trackingNo;

    public PaymentCashBasis() {}

    public PaymentCashBasis(Long id, Timestamp date, Long amount, Long serviceId, Short type, String trackingNo) {
        super(id, date, amount, serviceId);
        this.type = type;
        this.trackingNo = trackingNo;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
}
