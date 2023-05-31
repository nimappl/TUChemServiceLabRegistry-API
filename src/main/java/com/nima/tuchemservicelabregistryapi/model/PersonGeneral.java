package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class PersonGeneral extends Person {
    private String labPersonnelCode;
    private String labPost;
    private String profPersonnelCode;
    private Long profEduGroupId;
    private EduGroup profEduGroup;
    private String stdnCode;
    private Short stdnEduLevel;
    private Long stdnEduFieldId;
    private EduField stdnEduField;
    private List<Organization> orgRepOrganizations;

    public PersonGeneral() {}

    public PersonGeneral(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String labPersonnelCode, String labPost, String profPersonnelCode, Long profEduGroupId, EduGroup profEduGroup, String stdnCode, Short stdnLevel, Long stdnEduFieldId, EduField stdnEduField, List<Organization> orgRepOrganizations) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, typeStdn, typeProf, typeLab, typeOrg);
        this.labPersonnelCode = labPersonnelCode;
        this.labPost = labPost;
        this.profPersonnelCode = profPersonnelCode;
        this.profEduGroupId = profEduGroupId;
        this.profEduGroup = profEduGroup;
        this.stdnCode = stdnCode;
        this.stdnEduLevel = stdnLevel;
        this.stdnEduFieldId = stdnEduFieldId;
        this.stdnEduField = stdnEduField;
        this.orgRepOrganizations = orgRepOrganizations;
    }

    public String getLabPersonnelCode() {
        return labPersonnelCode;
    }

    public void setLabPersonnelCode(String labPersonnelCode) {
        this.labPersonnelCode = labPersonnelCode;
    }

    public String getLabPost() {
        return labPost;
    }

    public void setLabPost(String labPost) {
        this.labPost = labPost;
    }

    public String getProfPersonnelCode() {
        return profPersonnelCode;
    }

    public void setProfPersonnelCode(String profPersonnelCode) {
        this.profPersonnelCode = profPersonnelCode;
    }

    public Long getProfEduGroupId() {
        return profEduGroupId;
    }

    public void setProfEduGroupId(Long profEduGroupId) {
        this.profEduGroupId = profEduGroupId;
    }

    public EduGroup getProfEduGroup() {
        return profEduGroup;
    }

    public void setProfEduGroup(EduGroup profEduGroup) {
        this.profEduGroup = profEduGroup;
    }

    public String getStdnCode() {
        return stdnCode;
    }

    public void setStdnCode(String stdnCode) {
        this.stdnCode = stdnCode;
    }

    public Short getStdnEduLevel() {
        return stdnEduLevel;
    }

    public void setStdnEduLevel(Short stdnEduLevel) {
        this.stdnEduLevel = stdnEduLevel;
    }

    public Long getStdnEduFieldId() {
        return stdnEduFieldId;
    }

    public void setStdnEduFieldId(Long stdnEduFieldId) {
        this.stdnEduFieldId = stdnEduFieldId;
    }

    public EduField getStdnEduField() {
        return stdnEduField;
    }

    public void setStdnEduField(EduField stdnEduField) {
        this.stdnEduField = stdnEduField;
    }

    public List<Organization> getOrgRepOrganizations() {
        return orgRepOrganizations;
    }

    public void setOrgRepOrganizations(List<Organization> orgRepOrganizations) {
        this.orgRepOrganizations = orgRepOrganizations;
    }

    public LabPersonnel asLabPersonnel() {
        LabPersonnel data = new LabPersonnel();
        data.setId(getId());
        data.setPersonnelCode(labPersonnelCode);
        data.setPost(labPost);
        return data;
    }

    public TUProfessor asProfessor() {
        TUProfessor data = new TUProfessor();
        data.setId(getId());
        data.setEduGroup(profEduGroup);
        data.setEduGroupId(profEduGroupId);
        data.setPersonnelCode(profPersonnelCode);
        return data;
    }

    public TUStudent asStudent() {
        TUStudent data = new TUStudent();
        data.setId(getId());
        data.setStCode(stdnCode);
        data.setEduLevel(stdnEduLevel);
        data.setEduField(stdnEduField);
        data.setEduFieldId(stdnEduFieldId);
        return data;
    }

    public OrgRepresentative asOrgRepresentative() {
        OrgRepresentative data = new OrgRepresentative();
        data.setId(getId());
        data.setOrganizations(orgRepOrganizations);
        return data;
    }
}
