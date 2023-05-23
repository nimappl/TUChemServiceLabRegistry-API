package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.InstrumentOperator;
import com.nima.tuchemservicelabregistryapi.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstrumentOperatorDAO implements DAO<InstrumentOperator> {
    private JdbcTemplate jdbcTemplate;
    private PersonDAO personDAO;
    private RowMapper<InstrumentOperator> rowMapper = (rs, rowNum) -> {
        InstrumentOperator operator = new InstrumentOperator();
        operator.setId(rs.getLong("OperatorID"));
        operator.setNationalNumber(rs.getString("PNationalNumber"));
        operator.setFirstName(rs.getString("PFirstName"));
        operator.setLastName(rs.getString("PLastName"));
        operator.setPhoneNumber(rs.getString("PPhoneNumber"));
        operator.setEmail(rs.getString("PEmail"));
        operator.setGender((Boolean) rs.getObject("PGender"));
        operator.setCustomerId((Long) rs.getObject("CustomerID"));
        operator.setTypeStdn(rs.getBoolean("PTypeStdn"));
        operator.setTypeProf(rs.getBoolean("PTypeProf"));
        operator.setTypeLab(rs.getBoolean("PTypeLab"));
        operator.setTypeOrg(rs.getBoolean("PTypeOrg"));
        operator.setUsername(rs.getString("PUsername"));
        operator.setPassword(rs.getString("PPassword"));
        operator.setDesignationDate(rs.getTimestamp("DesignationDate"));
        operator.setType((Short) rs.getObject("IOperatorType"));
        return operator;
    };

    public InstrumentOperatorDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    public List<InstrumentOperator> getOperatorsOfInstrument(Long id) {
        return jdbcTemplate.query("SELECT * FROM vOperatorsOfInstruments WHERE InstrumentID=" + id, rowMapper);
    }

    public List<Person> queryOperatorCandidates(String name) {
        return jdbcTemplate.query("EXECUTE GetInstrumentOperatorCandidates '" + name + "'", personDAO.rowMapper);
    }

    @Override
    public Data<InstrumentOperator> list(Data<InstrumentOperator> template) {
        return null;
    }

    @Override
    public InstrumentOperator getById(Long id) {
        return null;
    }

    @Override
    public int create(InstrumentOperator instrumentOperator) {
        return 0;
    }

    @Override
    public int update(InstrumentOperator instrumentOperator) {
        return 0;
    }

    public int addForInstrument(InstrumentOperator operator, Long instId) {
        operator.setTypeLab(true);
        if (operator.getId() == null) operator.setId((long)personDAO.create(operator));
        return jdbcTemplate.update("INSERT INTO Instrument_Operator (InstrumentID, OperatorID, DesignationDate, IOperatorType) VALUES (?, ?, ?, ?)",
                instId, operator.getId(), operator.getDesignationDate(), operator.getType());
    }

    public int removeForInstrument(Long operatorId, Long instId) {
        return jdbcTemplate.update("DELETE FROM Instrument_Operator WHERE InstrumentID=? AND OperatorID=?", instId, operatorId);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
