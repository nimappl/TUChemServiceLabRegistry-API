package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class Discount {
    private Long id;
    private Short type;
    private Short percent;
    private Timestamp date;
    private Short minSamples;
    private String name;

    public Discount() {}

    public Discount(Long id, Short type, Short percent, Timestamp date, Short minSamples, String name) {
        this.id = id;
        this.type = type;
        this.percent = percent;
        this.date = date;
        this.minSamples = minSamples;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getPercent() {
        return percent;
    }

    public void setPercent(Short percent) {
        this.percent = percent;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Short getMinSamples() {
        return minSamples;
    }

    public void setMinSamples(Short minSamples) {
        this.minSamples = minSamples;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", type=" + type +
                ", percent=" + percent +
                ", date=" + date +
                ", minSamples=" + minSamples +
                ", name='" + name + '\'' +
                '}';
    }
}
