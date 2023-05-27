package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Instrument;
import com.nima.tuchemservicelabregistryapi.model.InstrumentOperator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InstrumentDAO implements DAO<Instrument> {

    private final JdbcTemplate jdbcTemplate;
    private final InstrumentOperatorDAO operatorDAO;

    private final RowMapper<Instrument> rowMapper = (rs, rowNum) -> {
        Instrument instrument = new Instrument();
        instrument.setId(rs.getLong("InstrumentID"));
        instrument.setName(rs.getString("IName"));
        instrument.setModel(rs.getString("IModel"));
        instrument.setSerial(rs.getString("ISerialNo"));
        instrument.setManufacturer(rs.getString("IManufacturer"));
        instrument.setMadeIn(rs.getString("IMadeIn"));
        instrument.setServiceable(rs.getBoolean("IServiceable"));
        return instrument;
    };

    public InstrumentDAO(JdbcTemplate jdbcTemplate, InstrumentOperatorDAO operatorDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.operatorDAO = operatorDAO;
    }

    @Override
    public Data<Instrument> list(Data<Instrument> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vInstrument", "InstrumentID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vInstrument", "InstrumentID"), rowMapper);
        template.records.forEach(instrument -> {
            instrument.setOperators(operatorDAO.getOperatorsOfInstrument(instrument.getId()));
        });
        return template;
    }

    @Override
    public Instrument getById(Long id) {
        String sql = "SELECT * FROM Instrument WHERE InstrumentID = ?";
        Instrument instrument = null;
        try {
            instrument = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            instrument.setOperators(operatorDAO.getOperatorsOfInstrument(instrument.getId()));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return instrument;
    }

    @Override
    public int create(Instrument instrument) {
        Long id = jdbcTemplate.queryForObject("EXECUTE CreateInstrument ?, ?, ?, ?, ?, ?",
                new Object[]{instrument.getName(), instrument.getModel(), instrument.getSerial(), instrument.getManufacturer(), instrument.getMadeIn(), instrument.getServiceable()}, Long.class);

        if (instrument.getOperators() != null) {
            instrument.getOperators().forEach(operator -> {
                operatorDAO.addForInstrument(operator, id);
            });
        }
        return 1;
    }

    @Override
    public int update(Instrument instrument) {
        Instrument instrumentInDB = getById(instrument.getId());
        if (instrumentInDB.getOperators() != null) {
            instrumentInDB.getOperators().forEach(operatorInDB -> {
                boolean isRemoved = true;
                for (InstrumentOperator operator : instrument.getOperators()) {
                    if (operatorInDB.getId().equals(operator.getId())) {
                        isRemoved = false;
                        break;
                    }
                }
                if (isRemoved) operatorDAO.removeForInstrument(operatorInDB.getId(), instrument.getId());
            });
        }
        if (instrument.getOperators() != null) {
            instrument.getOperators().forEach(operator -> {
                boolean isNew = true;
                for (InstrumentOperator operatorInDB : instrumentInDB.getOperators()) {
                    if (operatorInDB.getId().equals(operator.getId())) {
                        isNew = false;
                        break;
                    }
                }
                if (isNew) operatorDAO.addForInstrument(operator, instrument.getId());
            });
        }

        return jdbcTemplate.update("UPDATE Instrument SET IName=?, IModel=?, ISerialNo=?, IManufacturer=?, IMadeIn=?, IServiceable=? WHERE InstrumentID=?",
                instrument.getName(), instrument.getModel(), instrument.getSerial(), instrument.getManufacturer(), instrument.getMadeIn(), instrument.getServiceable(), instrument.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXEC DeleteInstrument @id=?", id);
    }
}