package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TUProfessor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TUProfessorDAO implements DAO<TUProfessor> {
    private final JdbcTemplate jdbcTemplate;
    private final EduGroupDAO eduGroupDao;

    private final RowMapper<TUProfessor> rowMapper = (rs, rowNum) -> {
        TUProfessor professor = new TUProfessor();
        professor.setId(rs.getLong("PersonID"));
        professor.setNationalNumber(rs.getString("PNationalNumber"));
        professor.setFirstName(rs.getString("PFirstName"));
        professor.setLastName(rs.getString("PLastName"));
        professor.setPhoneNumber(rs.getString("PPhoneNumber"));
        professor.setEmail(rs.getString("PEmail"));
        professor.setGender((Boolean) rs.getObject("PGender"));
        professor.setTypeStdn(rs.getBoolean("PTypeStdn"));
        professor.setTypeProf(rs.getBoolean("PTypeProf"));
        professor.setTypeLab(rs.getBoolean("PTypeLab"));
        professor.setTypeOrg(rs.getBoolean("PTypeOrg"));
        professor.setPersonnelCode(rs.getString("ProfPersonnelCode"));
        professor.setEduGroupId((Long) rs.getObject("ProfEduGroupID"));
        professor.setGrantBalance((Long) rs.getObject("ProfGrantBalance"));
        return professor;
    };

    public TUProfessorDAO(JdbcTemplate jdbcTemplate, EduGroupDAO eduGroupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.eduGroupDao = eduGroupDao;
    }

    @Override
    public Data<TUProfessor> list(Data<TUProfessor> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTUProfessor", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTUProfessor", "PersonID"), rowMapper);
        template.records.forEach((TUProfessor professor) -> {
            if (professor.getEduGroupId() != 0) {
                professor.setEduGroup(eduGroupDao.getById(professor.getEduGroupId()));
            }
        });

        return template;
    }

    public Data<TUProfessor> grantList(Data<TUProfessor> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTUProfessor", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTUProfessor", "PersonID"), rowMapper);
        template.records.forEach((TUProfessor professor) -> {
            if (professor.getEduGroupId() != 0) {
                professor.setEduGroup(eduGroupDao.getById(professor.getEduGroupId()));
            }
        });

        return template;
    }

    @Override
    public TUProfessor getById(Long id) {
        String sql = "SELECT * FROM vTUProfessorAll WHERE PersonID = ?";
        TUProfessor professor;
        try {
            professor = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            professor.setEduGroup(eduGroupDao.getById(professor.getEduGroupId()));
        } catch (DataAccessException ex) {
            return null;
        }
        return professor;
    }

    public int updateTable(TUProfessor professor) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TUProfessor WHERE PersonID=" + professor.getId(), Integer.class) == 1) {
            jdbcTemplate.update("UPDATE TUProfessor SET ProfPersonnelCode=?, ProfEduGroupID=?, DDate=NULL WHERE PersonID=?",
                    professor.getPersonnelCode(), professor.getEduGroupId(), professor.getId());
        } else {
            jdbcTemplate.update("INSERT INTO TUProfessor (PersonID, ProfPersonnelCode, ProfEduGroupID, ProfGrantBalance) VALUES (?, ?, ?, 0)",
                    professor.getId(), professor.getPersonnelCode(), professor.getEduGroupId());
        }
        return 1;
    }

    public void updateGrantBalance(long id, long amount) {
        TUProfessor prof = jdbcTemplate.queryForObject("SELECT * FROM vTUProfessor WHERE PersonID=" + id, rowMapper);
        amount += prof.getGrantBalance();
        jdbcTemplate.update("UPDATE TUProfessor SET ProfGrantBalance=? WHERE PersonID=?", amount, id);
    }

    @Override
    public int create(TUProfessor professor) {
        return updateTable(professor);
    }

    @Override
    public int update(TUProfessor professor) {
        return updateTable(professor);
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXECUTE DeleteTUProfessor ?", id);
    }
}