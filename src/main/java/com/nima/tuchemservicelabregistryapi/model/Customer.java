package com.nima.tuchemservicelabregistryapi.model;

public class Customer {
    private long id;
    private short type;
    private double balance;
    private Person person;
    private Organization organization;

    public Customer() {}

    public Customer(long id, short type, double balance, Person person, Organization organization) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.person = person;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
