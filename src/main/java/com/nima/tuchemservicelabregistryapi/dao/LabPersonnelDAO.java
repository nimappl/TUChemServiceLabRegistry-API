package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.LabPersonnel;
import com.nima.tuchemservicelabregistryapi.model.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LabPersonnelDAO implements DAO<LabPersonnel> {
    private JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;

    private RowMapper<LabPersonnel> rowMapper = (rs, rowNum) -> {
        LabPersonnel personnel = new LabPersonnel();
        personnel.setId(rs.getInt("PersonID"));
        personnel.setNationalNumber(rs.getString("PNationalNumber"));
        personnel.setFirstName(rs.getString("PFirstName"));
        personnel.setLastName(rs.getString("PLastName"));
        personnel.setPhoneNumber(rs.getString("PPhoneNumber"));
        personnel.setEmail(rs.getString("PEmail"));
        personnel.setGender(rs.getByte("PGender"));
        personnel.setCustomerId(rs.getInt("CustomerID"));
        personnel.setType(rs.getShort("PType"));
        personnel.setUsername(rs.getString("PUsername"));
        personnel.setPassword(rs.getString("PPassword"));
        personnel.setPersonnelCode(rs.getString("LPCode"));
        personnel.setPost(rs.getString("LPPost"));
        return personnel;
    };

    public LabPersonnelDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    @Override
    public Data<LabPersonnel> list(Data<LabPersonnel> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM labpersonnelv";
        String selectQuery = "SELECT * FROM labpersonnelv";

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
    public Optional<LabPersonnel> getById(Long id) {
        String sql = "SELECT * FROM labpersonnelv WHERE PersonID = ?";
        LabPersonnel personnel = null;
        try {
            personnel = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(personnel);
    }

    @Override
    public int create(LabPersonnel personnel) {
        if (personnel.getId() != 0) {
            if (personnel.getUsername() == null || personnel.getUsername() == "") {
                personnel.setUsername(personnel.getNationalNumber());
                personnel.setPassword(personnel.getNationalNumber());
            }
            jdbcTemplate.update("UPDATE Person SET PType=?, PUsername=?, PPassword=? WHERE PersonID=?",
                    1, personnel.getNationalNumber(), personnel.getNationalNumber(), personnel.getId());

            return jdbcTemplate.update("INSERT INTO LabPersonnel(PersonID, LPCode, LPPost) VALUES(?,?,?)",
                    personnel.getId(), personnel.getPersonnelCode(), personnel.getPost());
        }

        return jdbcTemplate.update("CALL CreateLabPersonnel(?,?,?,?,?,?,?,?,?,?,?)", personnel.getNationalNumber(), personnel.getFirstName(), personnel.getLastName(),
                personnel.getPhoneNumber(), personnel.getEmail(), personnel.getGender(), personnel.getCustomerId() == 0 ? null : personnel.getCustomerId(),
                personnel.getNationalNumber(), personnel.getNationalNumber(), personnel.getPersonnelCode(), personnel.getPost());
    }

    @Override
    public int update(LabPersonnel personnel) {
        return jdbcTemplate.update("CALL UpdateLabPersonnel(?,?,?,?,?,?,?,?,?,?,?,?)", personnel.getId(), personnel.getNationalNumber(), personnel.getFirstName(),
                personnel.getLastName(), personnel.getPhoneNumber(), personnel.getEmail(),personnel.getGender(), personnel.getCustomerId() == 0 ? null : personnel.getCustomerId(),
                personnel.getUsername(), personnel.getPassword(), personnel.getPersonnelCode(), personnel.getPost());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteLabPersonnel(?)", id);
    }
}
