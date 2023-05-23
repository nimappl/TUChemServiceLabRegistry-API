package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class OrgRepresentative extends Person {
    private List<Organization> organizations;

    public OrgRepresentative() {}

    public OrgRepresentative(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Long customerId, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String username, String password, List<Organization> organizations) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, typeStdn, typeProf, typeLab, typeOrg, username, password);
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
