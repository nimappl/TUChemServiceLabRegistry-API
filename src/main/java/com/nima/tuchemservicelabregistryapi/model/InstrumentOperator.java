package com.nima.tuchemservicelabregistryapi.model;
import java.sql.Timestamp;
import java.util.List;

public class InstrumentOperator extends Person {
    private Timestamp designationDate;
    private Short type;
    private List<Instrument> instruments;

    public InstrumentOperator() {}

    public InstrumentOperator(Long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, Boolean gender, Long customerId, Boolean typeStdn, Boolean typeProf, Boolean typeLab, Boolean typeOrg, String username, String password, Timestamp designationDate, Short type, List<Instrument> instruments) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, typeStdn, typeProf, typeLab, typeOrg, username, password);
        this.designationDate = designationDate;
        this.type = type;
        this.instruments = instruments;
    }

    public Timestamp getDesignationDate() {
        return designationDate;
    }

    public void setDesignationDate(Timestamp designationDate) {
        this.designationDate = designationDate;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }
}
