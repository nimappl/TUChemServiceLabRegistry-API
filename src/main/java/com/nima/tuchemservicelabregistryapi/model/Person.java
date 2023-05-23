package com.nima.tuchemservicelabregistryapi.model;

public class Person {
    private Long id;
    private String nationalNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private Long customerId;
    private Boolean typeStdn;
    private Boolean typeProf;
    private Boolean typeLab;
    private Boolean typeOrg;
    private String username;
    private String password;

    public Person() {}

    public Person(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Long customerId, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String username, String password) {
        this.id = id;
        this.nationalNumber = nationalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.customerId = customerId;
        this.typeStdn = typeStdn;
        this.typeProf = typeProf;
        this.typeLab = typeLab;
        this.typeOrg = typeOrg;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Boolean getTypeStdn() {
        return typeStdn;
    }

    public void setTypeStdn(Boolean typeStdn) {
        this.typeStdn = typeStdn;
    }

    public Boolean getTypeProf() {
        return typeProf;
    }

    public void setTypeProf(Boolean typeProf) {
        this.typeProf = typeProf;
    }

    public Boolean getTypeLab() {
        return typeLab;
    }

    public void setTypeLab(Boolean typeLab) {
        this.typeLab = typeLab;
    }

    public Boolean getTypeOrg() {
        return typeOrg;
    }

    public void setTypeOrg(Boolean typeOrg) {
        this.typeOrg = typeOrg;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person asPerson() {
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", nationalNumber='" + nationalNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", customerId=" + customerId +
                ", typeStdn=" + typeStdn +
                ", typeProf=" + typeProf +
                ", typeLab=" + typeLab +
                ", typeOrg=" + typeOrg +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
