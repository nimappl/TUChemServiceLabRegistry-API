package com.nima.tuchemservicelabregistryapi.model;
import java.sql.Timestamp;
import java.util.List;

public class InstrumentOperator extends Person {
    private Timestamp designationDate;
    private short type;
    private List<Instrument> instruments;

    public InstrumentOperator() {}

    public InstrumentOperator(long id, String nationalNumber, String firstName, String lastName, String phoneNumber, String email, byte gender, long customerId, short pType, String username, String password, Timestamp designationDate, short type, List<Instrument> instruments) {
        super(id, nationalNumber, firstName, lastName, phoneNumber, email, gender, customerId, pType, username, password);
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

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }
}
