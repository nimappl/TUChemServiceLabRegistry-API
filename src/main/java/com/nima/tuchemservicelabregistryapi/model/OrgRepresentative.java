package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class OrgRepresentative extends Person {
    private String jobTitle;
    private List<Organization> organizations;

    public OrgRepresentative() {}

    public OrgRepresentative(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, String username, String password, String jobTitle, List<Organization> organizations) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, (short) 4, username, password);
        this.jobTitle = jobTitle;
        this.organizations = organizations;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
