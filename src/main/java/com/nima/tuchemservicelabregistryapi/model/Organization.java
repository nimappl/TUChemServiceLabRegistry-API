package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class Organization {
    private Long id;
    private String name;
    private String nationalId;
    private String registrationNo;
    private String contractNo;
    private List<OrgPhoneNumber> phoneNumbers;
    private List<OrgRepresentative> representatives;

    public Organization() {}

    public Organization(Long id, String name, String nationalId, String registrationNo, String contractNo, List<OrgPhoneNumber> phoneNumbers, List<OrgRepresentative> representatives) {
        this.id = id;
        this.name = name;
        this.nationalId = nationalId;
        this.registrationNo = registrationNo;
        this.contractNo = contractNo;
        this.phoneNumbers = phoneNumbers;
        this.representatives = representatives;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public List<OrgPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<OrgPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<OrgRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<OrgRepresentative> representatives) {
        this.representatives = representatives;
    }

    public boolean isEqualTo(Organization o) {
        return name.equals(o.getName()) && nationalId.equals(o.getName()) && registrationNo.equals(o.getRegistrationNo()) && contractNo.equals(o.getContractNo());
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", representatives=" + representatives +
                '}';
    }
}
