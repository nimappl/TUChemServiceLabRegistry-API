package com.nima.tuchemservicelabregistryapi.model;
import java.sql.Timestamp;

public class TUProfessor extends Person {
    private String personnelCode;
    private long eduGroupId;
    private EduGroup eduGroup;
    private Timestamp grantIssueDate;
    private double grantAmount;
    private Timestamp grantCredibleUntil;

    public TUProfessor() {}

    public TUProfessor(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, String username, String password, String personnelCode, long eduGroupId, EduGroup eduGroup, Timestamp grantIssueDate, double grantAmount, Timestamp grantCredibleUntil) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, (short) 3, username, password);
        this.personnelCode = personnelCode;
        this.eduGroupId = eduGroupId;
        this.eduGroup = eduGroup;
        this.grantIssueDate = grantIssueDate;
        this.grantAmount = grantAmount;
        this.grantCredibleUntil = grantCredibleUntil;
    }

    public long getEduGroupId() {
        return eduGroupId;
    }

    public void setEduGroupId(long eduGroupId) {
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

    public double getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(double grantAmount) {
        this.grantAmount = grantAmount;
    }

    public Timestamp getGrantCredibleUntil() {
        return grantCredibleUntil;
    }

    public void setGrantCredibleUntil(Timestamp grantCredibleUntil) {
        this.grantCredibleUntil = grantCredibleUntil;
    }
}
