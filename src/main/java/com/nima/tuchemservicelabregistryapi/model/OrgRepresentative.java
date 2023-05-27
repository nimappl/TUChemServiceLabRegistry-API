package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class OrgRepresentative extends Person {
    private List<Organization> organizations;

    public OrgRepresentative() {}

    public OrgRepresentative(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, List<Organization> organizations) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "OrgRepresentative{" +
                "organizations=" + organizations +
                '}';
    }
}
