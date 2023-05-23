package com.nima.tuchemservicelabregistryapi.model;

import java.util.Objects;

public class OrgPhoneNumber {
    private String number;
    private String section;

    public OrgPhoneNumber() {}

    public OrgPhoneNumber(String number, String section) {
        this.number = number;
        this.section = section;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isEqualTo(OrgPhoneNumber o) {
        return number.equals(o.getNumber());
    }
}
