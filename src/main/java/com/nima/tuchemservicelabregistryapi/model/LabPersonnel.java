package com.nima.tuchemservicelabregistryapi.model;

public class LabPersonnel extends Person {
    private String personnelCode;
    private String post;
    private Short role;

    public LabPersonnel() {}

    public LabPersonnel(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String personnelCode, String post, Short role) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.personnelCode = personnelCode;
        this.post = post;
        this.role = role;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LabPersonnel{" +
                "personnelCode='" + personnelCode + '\'' +
                ", post='" + post + '\'' +
                ", role=" + role +
                '}';
    }
}
