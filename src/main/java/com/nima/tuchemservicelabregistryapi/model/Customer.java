package com.nima.tuchemservicelabregistryapi.model;

public class Customer {
    private Long id;
    private Short type;
    private Long balance;
    private Person person;
    private Organization organization;

    public Customer() {}

    public Customer(Long id, Short type, Long balance, Person person, Organization organization) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.person = person;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
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
