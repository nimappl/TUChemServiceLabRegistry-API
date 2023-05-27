package com.nima.tuchemservicelabregistryapi.model;
import java.sql.Timestamp;

public class TUProfessor extends Person {
    private String personnelCode;
    private Long eduGroupId;
    private EduGroup eduGroup;
    private Timestamp grantIssueDate;
    private Long grantAmount;
    private Timestamp grantCredibleUntil;

    public TUProfessor() {}

    public TUProfessor(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String personnelCode, Long eduGroupId, EduGroup eduGroup, Timestamp grantIssueDate, Long grantAmount, Timestamp grantCredibleUntil) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.personnelCode = personnelCode;
        this.eduGroupId = eduGroupId;
        this.eduGroup = eduGroup;
        this.grantIssueDate = grantIssueDate;
        this.grantAmount = grantAmount;
        this.grantCredibleUntil = grantCredibleUntil;
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

    public Timestamp getGrantIssueDate() {
        return grantIssueDate;
    }

    public void setGrantIssueDate(Timestamp grantIssueDate) {
        this.grantIssueDate = grantIssueDate;
    }

    public Long getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(Long grantAmount) {
        this.grantAmount = grantAmount;
    }

    public Timestamp getGrantCredibleUntil() {
        return grantCredibleUntil;
    }

    public void setGrantCredibleUntil(Timestamp grantCredibleUntil) {
        this.grantCredibleUntil = grantCredibleUntil;
    }

    @Override
    public String toString() {
        return "TUProfessor{" +
                "personnelCode='" + personnelCode + '\'' +
                ", eduGroupId=" + eduGroupId +
                ", eduGroup=" + eduGroup +
                ", grantIssueDate=" + grantIssueDate +
                ", grantAmount=" + grantAmount +
                ", grantCredibleUntil=" + grantCredibleUntil +
                '}';
    }
}
