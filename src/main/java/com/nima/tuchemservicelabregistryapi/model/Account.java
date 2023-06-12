package com.nima.tuchemservicelabregistryapi.model;

import java.util.List;

public class Account {
    private Long id;
    private Short type;
    private Long balance;
    private Long personCustomerId;
    private PersonGeneral custPerson;
    private Long organizationCustomerId;
    private Organization custOrganization;
    private List<Payment> payments;
    private List<Service> services;

    public Account() {}

    public Account(Long id, Short type, Long balance, Long personCustomerId, PersonGeneral custPerson, Long organizationCustomerId, Organization custOrganization, List<Payment> payments, List<Service> services) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.personCustomerId = personCustomerId;
        this.custPerson = custPerson;
        this.organizationCustomerId = organizationCustomerId;
        this.custOrganization = custOrganization;
        this.payments = payments;
        this.services = services;
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

    public Long getPersonCustomerId() {
        return personCustomerId;
    }

    public void setPersonCustomerId(Long personCustomerId) {
        this.personCustomerId = personCustomerId;
    }

    public PersonGeneral getCustPerson() {
        return custPerson;
    }

    public void setCustPerson(PersonGeneral custPerson) {
        this.custPerson = custPerson;
    }

    public Long getOrganizationCustomerId() {
        return organizationCustomerId;
    }

    public void setOrganizationCustomerId(Long organizationCustomerId) {
        this.organizationCustomerId = organizationCustomerId;
    }

    public Organization getCustOrganization() {
        return custOrganization;
    }

    public void setCustOrganization(Organization custOrganization) {
        this.custOrganization = custOrganization;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type=" + type +
                ", balance=" + balance +
                ", personCustomerId=" + personCustomerId +
                ", custPerson=" + custPerson +
                ", organizationCustomerId=" + organizationCustomerId +
                ", custOrganization=" + custOrganization +
                '}';
    }

    public boolean isEqualTo(Account a) {
        return type.equals(a.type) && balance.equals(a.balance) && personCustomerId.equals(a.personCustomerId) && organizationCustomerId.equals(a.organizationCustomerId);
    }
}
