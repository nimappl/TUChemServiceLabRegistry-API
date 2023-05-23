package com.nima.tuchemservicelabregistryapi.model;

public class EduField {
    private Long id;
    private String name;
    private Long eduGroupId;
    private EduGroup eduGroup;

    public EduField() {}

    public EduField(Long id, String name, Long eduGroupId, EduGroup eduGroup) {
        this.id = id;
        this.name = name;
        this.eduGroupId = eduGroupId;
        this.eduGroup = eduGroup;
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

    public Long getEduGroupId() {
        return eduGroupId;
    }

    public void setEduGroupId(Long eduGroupId) {
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
                ", eduGroupId=" + eduGroupId +
                ", eduGroup=" + eduGroup +
                '}';
    }
}
