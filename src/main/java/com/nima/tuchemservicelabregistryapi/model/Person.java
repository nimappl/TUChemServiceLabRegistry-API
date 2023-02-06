package com.nima.tuchemservicelabregistryapi.model;

public class Person {
    private long id;
    private String nationalNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private byte gender;
    private long customerId;
    private short type = 0;
    private String username;
    private String password;

    public Person() {}

    public Person(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, short type, String username, String password) {
        this.id = id;
        this.nationalNumber = nationalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.customerId = customerId;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
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
                '}';
    }
}
