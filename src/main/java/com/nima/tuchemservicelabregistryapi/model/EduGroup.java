package com.nima.tuchemservicelabregistryapi.model;

public class EduGroup {
    private Long id;
    private String name;

    public EduGroup() {}

    public EduGroup(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "EduGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
