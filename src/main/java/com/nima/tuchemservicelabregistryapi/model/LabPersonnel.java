package com.nima.tuchemservicelabregistryapi.model;

public class LabPersonnel extends Person {
    private String personnelCode;
    private String post;
    private short role;

    public LabPersonnel() {}

    public LabPersonnel(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, String username, String password, String personnelCode, String post, short role) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, (short) 1, username, password);
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

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }
}
