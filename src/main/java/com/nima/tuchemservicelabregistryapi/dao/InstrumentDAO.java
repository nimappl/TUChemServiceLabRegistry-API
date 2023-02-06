package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.Instrument;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class InstrumentDAO implements DAO<Instrument> {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Instrument> rowMapper = (rs, rowNum) -> {
        Instrument instrument = new Instrument();
        instrument.setId(rs.getInt("InstrumentID"));
        instrument.setName(rs.getString("IName"));
        instrument.setModel(rs.getString("IModel"));
        instrument.setSerial(rs.getString("ISerialNo"));
        instrument.setManufacturer(rs.getString("IManufacturer"));
        instrument.setMadeIn(rs.getString("IMadeIn"));
        instrument.setServiceable(rs.getByte("IServiceable"));
        return instrument;
    };

    public InstrumentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<Instrument> list(Data<Instrument> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM instrumentv";
        String selectQuery = "SELECT * FROM instrumentv";

        // WHERE clause for each of filters
        for (Filter filter : template.filters) {
            if (firstFilter) { queryBody += " WHERE "; firstFilter = false; }
            else queryBody += " AND ";
            queryBody += filter.key + " LIKE \"%" + filter.value + "%\" ";
        }
        countQuery += queryBody;

        // ORDER BY
        if (template.sortBy != null) {
            String sortType = template.sortType == 0 ? "ASC " : "DESC ";
            queryBody += " ORDER BY " + template.sortBy + " " + sortType;
        }

        // PAGINATION
        if (template.pageSize != 0) {
            queryBody += " LIMIT " + template.pageSize + " OFFSET " + ((template.pageNumber - 1) * template.pageSize);
        }
        selectQuery += queryBody;

        template.count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        template.records = jdbcTemplate.query(selectQuery, rowMapper);
        return template;
    }

    @Override
    public Optional<Instrument> getById(Long id) {
        String sql = "SELECT * FROM instrument WHERE InstrumentID = ?";
        Instrument instrument = null;
        try {
            instrument = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(instrument);
    }

    @Override
    public int create(Instrument instrument) {
        String sql = "INSERT INTO instrument(IName, IModel, ISerialNo, IManufacturer, IMadeIn, IServiceable)" +
                " VALUES(?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, instrument.getName(), instrument.getModel(), instrument.getSerial(),
                instrument.getManufacturer(), instrument.getMadeIn(), instrument.getServiceable());
    }

    @Override
    public int update(Instrument instrument) {
//        String sql = "UPDATE instrument SET Name=?, Model=?, Serial=?, Manufacturer=?, MadeIn=?, Active=?" +
//                     " WHERE InstrumentID=?";
//
//        return jdbcTemplate.update(sql, instrument.getName(), instrument.getModel(), instrument.getSerial(),
//                                        instrument.getManufacturer(), instrument.getMadeIn(), instrument.getServicable(),
//                                        instrument.getInstrumentId());
        jdbcTemplate.update("CALL UpdateInstrument(?, ?, ?, ?, ?, ?, ?)", instrument.getId(), instrument.getName(), instrument.getModel(),
                instrument.getSerial(), instrument.getManufacturer(), instrument.getMadeIn(), instrument.getServiceable());
        return 1;
    }

    @Override
    public int delete(Long id) {
//        LocalDateTime current = LocalDateTime.now();
//        return jdbcTemplate.update("UPDATE instrument SET DDate=? WHERE InstrumentID=?", current, id);
        jdbcTemplate.update("CALL DeleteInstrument(?)", id);
        return 1;
    }
}