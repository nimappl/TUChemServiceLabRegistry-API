package com.nima.tuchemservicelabregistryapi.model;

public class TUStudent extends Person {
    private String stCode;
    private Short eduLevel;
    private Long eduFieldId;
    private EduField eduField;

    public TUStudent() {}

    public TUStudent(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String stCode, Short eduLevel, Long eduFieldId, EduField eduField) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.stCode = stCode;
        this.eduLevel = eduLevel;
        this.eduFieldId = eduFieldId;
        this.eduField = eduField;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public Short getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Short eduLevel) {
        this.eduLevel = eduLevel;
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

    @Override
    public String toString() {
        return "TUStudent{" +
                "stCode='" + stCode + '\'' +
                ", eduLevel=" + eduLevel +
                ", eduFieldId=" + eduFieldId +
                ", eduField=" + eduField +
                '}';
    }
}
