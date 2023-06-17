package com.nima.tuchemservicelabregistryapi.model;

public class TUProfessor extends Person {
    private String personnelCode;
    private Long eduGroupId;
    private EduGroup eduGroup;
    private Long grantBalance;

    public TUProfessor() {}

    public TUProfessor(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String personnelCode, Long eduGroupId, EduGroup eduGroup, Long grantBalance) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.personnelCode = personnelCode;
        this.eduGroupId = eduGroupId;
        this.eduGroup = eduGroup;
        this.grantBalance = grantBalance;
    }

    public Long getEduGroupId() {
        return eduGroupId;
    }

    public void setEduGroupId(Long eduGroupId) {
        this.eduGroupId = eduGroupId;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public EduGroup getEduGroup() {
        return eduGroup;
    }

    public void setEduGroup(EduGroup eduGroup) {
        this.eduGroup = eduGroup;
    }

    public Long getGrantBalance() {
        return grantBalance;
    }

    public void setGrantBalance(Long grantBalance) {
        this.grantBalance = grantBalance;
    }

    @Override
    public String toString() {
        return "TUProfessor{" +
                "personnelCode='" + personnelCode + '\'' +
                ", eduGroupId=" + eduGroupId +
                ", eduGroup=" + eduGroup +
                '}';
    }
}
