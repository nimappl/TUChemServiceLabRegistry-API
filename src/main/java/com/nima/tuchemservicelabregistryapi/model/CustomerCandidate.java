package com.nima.tuchemservicelabregistryapi.model;

public class CustomerCandidate {
    private Integer type;
    private Long ID;
    private String name;
    private Boolean typeStdn;
    private Boolean typeProf;
    private Boolean typeOrg;
    private String stdnEduGroup;
    private String profEduGroup;

    public CustomerCandidate() {}

    public CustomerCandidate(Integer type, Long ID, String name, Boolean typeStdn, Boolean typeProf, Boolean typeOrg, String stdnEduGroup, String profEduGroup) {
        this.type = type;
        this.ID = ID;
        this.name = name;
        this.typeStdn = typeStdn;
        this.typeProf = typeProf;
        this.typeOrg = typeOrg;
        this.stdnEduGroup = stdnEduGroup;
        this.profEduGroup = profEduGroup;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTypeStdn() {
        return typeStdn;
    }

    public void setTypeStdn(Boolean typeStdn) {
        this.typeStdn = typeStdn;
    }

    public Boolean getTypeProf() {
        return typeProf;
    }

    public void setTypeProf(Boolean typeProf) {
        this.typeProf = typeProf;
    }

    public Boolean getTypeOrg() {
        return typeOrg;
    }

    public void setTypeOrg(Boolean typeOrg) {
        this.typeOrg = typeOrg;
    }

    public String getStdnEduGroup() {
        return stdnEduGroup;
    }

    public void setStdnEduGroup(String stdnEduGroup) {
        this.stdnEduGroup = stdnEduGroup;
    }

    public String getProfEduGroup() {
        return profEduGroup;
    }

    public void setProfEduGroup(String profEduGroup) {
        this.profEduGroup = profEduGroup;
    }
}
