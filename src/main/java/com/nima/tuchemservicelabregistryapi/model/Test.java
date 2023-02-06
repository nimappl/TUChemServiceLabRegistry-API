package com.nima.tuchemservicelabregistryapi.model;
import java.util.List;

public class Test {
    private long id;
    private String name;
    private String shortName;
    private byte hasPrep;
    private long instrumentId;
    private Instrument instrument;
    private byte tActive;
    private String description;
    private List<TestPrep> samplePreparations;
    private List<TestFee> fees;
    private List<Discount> discounts;

    public Test() {}

    public Test(long id, String name, String shortName, byte hasPrep, long instrumentId, Instrument instrument, byte tActive, String description, List<TestPrep> samplePreparations, List<TestFee> fees, List<Discount> discounts) {
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

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public byte getHasPrep() {
        return hasPrep;
    }

    public void setHasPrep(byte hasPrep) {
        this.hasPrep = hasPrep;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public byte gettActive() {
        return tActive;
    }

    public void settActive(byte tActive) {
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
}
