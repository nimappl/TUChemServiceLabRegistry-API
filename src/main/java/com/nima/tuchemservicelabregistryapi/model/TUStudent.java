package com.nima.tuchemservicelabregistryapi.model;

public class TUStudent extends Person {
    private String stCode;
    private long eduFieldId;
    private EduField eduField;

    public TUStudent() {}

    public TUStudent(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, String username, String password, String stCode, long eduFieldId, EduField eduField) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, (short) 2, username, password);
        this.stCode = stCode;
        this.eduFieldId = eduFieldId;
        this.eduField = eduField;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public long getEduFieldId() {
        return eduFieldId;
    }

    public void setEduFieldId(long eduFieldId) {
        this.eduFieldId = eduFieldId;
    }

    public EduField getEduField() {
        return eduField;
    }

    public void setEduField(EduField eduField) {
        this.eduField = eduField;
    }
}
