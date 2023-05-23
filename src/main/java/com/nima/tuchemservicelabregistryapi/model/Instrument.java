package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class Instrument {
    private Long id;
    private String name;
    private String model;
    private String serial;
    private String manufacturer;
    private String madeIn;
    private Boolean serviceable;
    private List<InstrumentOperator> operators;

    public Instrument() {}

    public Instrument(Long id, String name, String model, String serial, String manufacturer, String madeIn, Boolean serviceable, List<InstrumentOperator> operators) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.serial = serial;
        this.manufacturer = manufacturer;
        this.madeIn = madeIn;
        this.serviceable = serviceable;
        this.operators = operators;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public Boolean getServiceable() {
        return serviceable;
    }

    public void setServiceable(Boolean serviceable) {
        this.serviceable = serviceable;
    }

    public List<InstrumentOperator> getOperators() {
        return operators;
    }

    public void setOperators(List<InstrumentOperator> operators) {
        this.operators = operators;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", serial='" + serial + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", madeIn='" + madeIn + '\'' +
                ", serviceable=" + serviceable +
                ", operators=" + operators +
                '}';
    }
}
