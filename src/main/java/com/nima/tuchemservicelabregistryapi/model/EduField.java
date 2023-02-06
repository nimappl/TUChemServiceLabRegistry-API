package com.nima.tuchemservicelabregistryapi.model;

public class EduField {
    private long id;
    private String name;
    private short fieldLevel;
    private long eduGroupId;
    private EduGroup eduGroup;

    public EduField() {}

    public EduField(long id, String name, short fieldLevel, long eduGroupId, EduGroup eduGroup) {
        this.id = id;
        this.name = name;
        this.fieldLevel = fieldLevel;
        this.eduGroupId = eduGroupId;
        this.eduGroup = eduGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getFieldLevel() {
        return fieldLevel;
    }

    public void setFieldLevel(short fieldLevel) {
        this.fieldLevel = fieldLevel;
    }

    public long getEduGroupId() {
        return eduGroupId;
    }

    public void setEduGroupId(long eduGroupId) {
        this.eduGroupId = eduGroupId;
    }

    public EduGroup getEduGroup() {
        return eduGroup;
    }

    public void setEduGroup(EduGroup eduGroup) {
        this.eduGroup = eduGroup;
    }

    @Override
    public String toString() {
        return "EduField{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fieldLevel=" + fieldLevel +
                ", eduGroupId=" + eduGroupId +
                ", eduGroup=" + eduGroup +
                '}';
    }
}
