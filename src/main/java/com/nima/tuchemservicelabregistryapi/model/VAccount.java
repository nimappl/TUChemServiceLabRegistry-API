package com.nima.tuchemservicelabregistryapi.model;

public class VAccount {
    private Long id;
    private Short type;
    private Long balance;
    private String customerName;
    private String nationalNumber;
    private Long personCustomerId;
    private PersonGeneral custPerson;
    private Long organizationCustomerId;
    private Organization custOrganization;

    public VAccount() {}

    public VAccount(Long id, Short type, Long balance, String customerName, String nationalNumber, Long personCustomerId, PersonGeneral custPerson, Long organizationCustomerId, Organization custOrganization) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.customerName = customerName;
        this.nationalNumber = nationalNumber;
        this.personCustomerId = personCustomerId;
        this.custPerson = custPerson;
        this.organizationCustomerId = organizationCustomerId;
        this.custOrganization = custOrganization;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public Long getPersonCustomerId() {
        return personCustomerId;
    }

    public void setPersonCustomerId(Long personCustomerId) {
        this.personCustomerId = personCustomerId;
    }

    public Long getOrganizationCustomerId() {
        return organizationCustomerId;
    }

    public PersonGeneral getCustPerson() {
        return custPerson;
    }

    public void setCustPerson(PersonGeneral custPerson) {
        this.custPerson = custPerson;
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
}
