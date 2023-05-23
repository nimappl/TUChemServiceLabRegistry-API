package com.nima.tuchemservicelabregistryapi.model;

public class TUStudent extends Person {
    private String stCode;
    private Short level;
    private Long eduFieldId;
    private EduField eduField;

    public TUStudent() {}

    public TUStudent(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Long customerId, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String username, String password, String stCode, Short level, Long eduFieldId, EduField eduField) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, typeStdn, typeProf, typeLab, typeOrg, username, password);
        this.stCode = stCode;
        this.level = level;
        this.eduFieldId = eduFieldId;
        this.eduField = eduField;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Long getEduFieldId() {
        return eduFieldId;
    }

    public void setEduFieldId(Long eduFieldId) {
        this.eduFieldId = eduFieldId;
    }

    public EduField getEduField() {
        return eduField;
    }

    public void setEduField(EduField eduField) {
        this.eduField = eduField;
    }
}
