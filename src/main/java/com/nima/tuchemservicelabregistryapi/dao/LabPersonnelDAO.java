package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.LabPersonnel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LabPersonnelDAO implements DAO<LabPersonnel> {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;

    private final RowMapper<LabPersonnel> rowMapper = (rs, rowNum) -> {
        LabPersonnel personnel = new LabPersonnel();
        personnel.setId(rs.getLong("PersonID"));
        personnel.setNationalNumber(rs.getString("PNationalNumber"));
        personnel.setFirstName(rs.getString("PFirstName"));
        personnel.setLastName(rs.getString("PLastName"));
        personnel.setPhoneNumber(rs.getString("PPhoneNumber"));
        personnel.setEmail(rs.getString("PEmail"));
        personnel.setGender((Boolean) rs.getObject("PGender"));
        personnel.setTypeStdn(rs.getBoolean("PTypeStdn"));
        personnel.setTypeProf(rs.getBoolean("PTypeProf"));
        personnel.setTypeLab(rs.getBoolean("PTypeLab"));
        personnel.setTypeOrg(rs.getBoolean("PTypeOrg"));
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
        template.filters.add(new Filter("PTypeLab", "1"));
        template.count = jdbcTemplate.queryForObject(template.countQuery("vLabPersonnel", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vLabPersonnel", "PersonID"), rowMapper);
        template.filters = new ArrayList<>();
        return template;
    }

    @Override
    public LabPersonnel getById(Long id) {
        String sql = "SELECT * FROM vLabPersonnelAll WHERE PersonID = ?";
        LabPersonnel personnel = null;
        try {
            personnel = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return personnel;
    }

    @Override
    public int create(LabPersonnel personnel) {
        jdbcTemplate.update("INSERT INTO LabPersonnel (PersonID, LPCode, LPPost, LPRole) VALUES (?, ?, ?, ?)",
                personnel.getId(), personnel.getPersonnelCode(), personnel.getPost(), personnel.getRole());
        return 1;
    }

    @Override
    public int update(LabPersonnel personnel) {
        return jdbcTemplate.update("UPDATE LabPersonnel SET LPCode=?, LPPost=?, LPRole=? WHERE PersonID=?",
                personnel.getPersonnelCode(), personnel.getPost(), personnel.getRole(), personnel.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXECUTE DeleteLabPersonnel ?", id);
    }
}
