package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class Test {
    private Long id;
    private String name;
    private String shortName;
    private Byte hasPrep;
    private Long instrumentId;
    private Instrument instrument;
    private Boolean tActive;
    private String description;
    private List<TestPrep> samplePreparations;
    private List<TestFee> fees;
    private List<Discount> discounts;

    public Test() {}

    public Test(Long id, String name, String shortName, Byte hasPrep, Long instrumentId, Instrument instrument, Boolean tActive, String description, List<TestPrep> samplePreparations, List<TestFee> fees, List<Discount> discounts) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.hasPrep = hasPrep;
        this.instrumentId = instrumentId;
        this.instrument = instrument;
        this.tActive = tActive;
        this.description = description;
        this.samplePreparations = samplePreparations;
        this.fees = fees;
        this.discounts = discounts;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Byte getHasPrep() {
        return hasPrep;
    }

    public void setHasPrep(Byte hasPrep) {
        this.hasPrep = hasPrep;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Boolean gettActive() {
        return tActive;
    }

    public void settActive(Boolean tActive) {
        this.tActive = tActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestPrep> getSamplePreparations() {
        return samplePreparations;
    }

    public void setSamplePreparations(List<TestPrep> samplePreparations) {
        this.samplePreparations = samplePreparations;
    }

    public List<TestFee> getFees() {
        return fees;
    }

    public void setFees(List<TestFee> fees) {
        this.fees = fees;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", hasPrep=" + hasPrep +
                ", instrumentId=" + instrumentId +
                ", instrument=" + instrument +
                ", tActive=" + tActive +
                ", description='" + description + '\'' +
                ", samplePreparations=" + samplePreparations +
                ", fees=" + fees +
                ", discounts=" + discounts +
                '}';
    }
}
