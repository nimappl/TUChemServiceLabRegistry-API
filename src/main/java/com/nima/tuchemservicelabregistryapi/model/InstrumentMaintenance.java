package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class InstrumentMaintenance {
    private long id;
    private String title;
    private Timestamp date;
    private double cost;
    private String invoiceNo;
    private Instrument instrument;
    private OrgRepresentative serviceman;
    private String description;
    private List<IMUsedMaterial> usedMaterialList;

    public InstrumentMaintenance() {}

    public InstrumentMaintenance(long id, String title, Timestamp date, double cost, String invoiceNo, Instrument instrument, OrgRepresentative serviceman, String description, List<IMUsedMaterial> usedMaterialList) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.cost = cost;
        this.invoiceNo = invoiceNo;
        this.instrument = instrument;
        this.serviceman = serviceman;
        this.description = description;
        this.usedMaterialList = usedMaterialList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public OrgRepresentative getServiceman() {
        return serviceman;
    }

    public void setServiceman(OrgRepresentative serviceman) {
        this.serviceman = serviceman;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IMUsedMaterial> getUsedMaterialList() {
        return usedMaterialList;
    }

    public void setUsedMaterialList(List<IMUsedMaterial> usedMaterialList) {
        this.usedMaterialList = usedMaterialList;
    }
}
