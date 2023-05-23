package com.nima.tuchemservicelabregistryapi.model;

public class LabPersonnel extends Person {
    private String personnelCode;
    private String post;
    private Short role;

    public LabPersonnel() {}

    public LabPersonnel(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Long customerId, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String username, String password, String personnelCode, String post, Short role) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, typeStdn, typeProf, typeLab, typeOrg, username, password);
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
}
