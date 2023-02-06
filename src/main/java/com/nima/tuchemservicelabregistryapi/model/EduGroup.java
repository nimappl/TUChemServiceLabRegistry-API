package com.nima.tuchemservicelabregistryapi.model;

public class EduGroup {
    private long id;
    private String name;

    public EduGroup() {}

    public EduGroup(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "EduGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
