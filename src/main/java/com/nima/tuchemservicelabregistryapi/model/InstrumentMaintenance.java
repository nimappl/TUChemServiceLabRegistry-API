package com.nima.tuchemservicelabregistryapi.model;

import java.sql.Timestamp;
import java.util.List;

public class InstrumentMaintenance {
    private Long id;
    private String title;
    private Timestamp date;
    private Long totalCost;
    private Long additionalCosts;
    private String invoiceNo;
    private Long instrumentId;
    private Instrument instrument;
    private Long servicemanId;
    private OrgRepresentative serviceman;
    private Long servicingCompanyId;
    private Organization servicingCompany;
    private String description;
    private List<IMUsedMaterial> usedMaterialList;

    public InstrumentMaintenance() {}

    public InstrumentMaintenance(Long id, String title, Timestamp date, Long totalCost, Long additionalCosts, String invoiceNo, Long instrumentId, Instrument instrument, Long servicemanId, OrgRepresentative serviceman, Long servicingCompanyId, Organization servicingCompany, String description, List<IMUsedMaterial> usedMaterialList) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.totalCost = totalCost;
        this.additionalCosts = additionalCosts;
        this.invoiceNo = invoiceNo;
        this.instrumentId = instrumentId;
        this.instrument = instrument;
        this.servicemanId = servicemanId;
        this.serviceman = serviceman;
        this.servicingCompanyId = servicingCompanyId;
        this.servicingCompany = servicingCompany;
        this.description = description;
        this.usedMaterialList = usedMaterialList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(Long additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Long getServicemanId() {
        return servicemanId;
    }

    public void setServicemanId(Long servicemanId) {
        this.servicemanId = servicemanId;
    }

    public OrgRepresentative getServiceman() {
        return serviceman;
    }

    public void setServiceman(OrgRepresentative serviceman) {
        this.serviceman = serviceman;
    }

    public Long getServicingCompanyId() {
        return servicingCompanyId;
    }

    public void setServicingCompanyId(Long servicingCompanyId) {
        this.servicingCompanyId = servicingCompanyId;
    }

    public Organization getServicingCompany() {
        return servicingCompany;
    }

    public void setServicingCompany(Organization servicingCompany) {
        this.servicingCompany = servicingCompany;
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

    @Override
    public String toString() {
        return "InstrumentMaintenance{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", totalCost=" + totalCost +
                ", additionalCosts=" + additionalCosts +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", instrumentId=" + instrumentId +
                ", instrument=" + instrument +
                ", servicemanId=" + servicemanId +
                ", serviceman=" + serviceman +
                ", servicingCompanyId=" + servicingCompanyId +
                ", servicingCompany=" + servicingCompany +
                ", description='" + description + '\'' +
                ", usedMaterialList=" + usedMaterialList +
                '}';
    }
}
