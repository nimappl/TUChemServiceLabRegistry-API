package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;

public class Discount {
    private long id;
    private short type;
    private short percent;
    private Timestamp date;
    private short minSamples;
    private String name;

    public Discount() {}

    public Discount(long id, short type, short percent, Timestamp date, short minSamples, String name) {
        this.id = id;
        this.type = type;
        this.percent = percent;
        this.date = date;
        this.minSamples = minSamples;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getPercent() {
        return percent;
    }

    public void setPercent(short percent) {
        this.percent = percent;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public short getMinSamples() {
        return minSamples;
    }

    public void setMinSamples(short minSamples) {
        this.minSamples = minSamples;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
