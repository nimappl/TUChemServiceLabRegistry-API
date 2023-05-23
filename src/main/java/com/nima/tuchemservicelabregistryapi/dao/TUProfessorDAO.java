package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TUProfessor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TUProfessorDAO implements DAO<TUProfessor> {
    JdbcTemplate jdbcTemplate;
    PersonDAO personDao;
    EduGroupDAO eduGroupDao;

    private RowMapper<TUProfessor> rowMapper = (rs, rowNum) -> {
        TUProfessor professor = new TUProfessor();
        professor.setId(rs.getLong("PersonID"));
        professor.setNationalNumber(rs.getString("PNationalNumber"));
        professor.setFirstName(rs.getString("PFirstName"));
        professor.setLastName(rs.getString("PLastName"));
        professor.setPhoneNumber(rs.getString("PPhoneNumber"));
        professor.setEmail(rs.getString("PEmail"));
        professor.setGender((Boolean) rs.getObject("PGender"));
        professor.setCustomerId((Long) rs.getObject("CustomerID"));
        professor.setTypeStdn(rs.getBoolean("PTypeStdn"));
        professor.setTypeProf(rs.getBoolean("PTypeProf"));
        professor.setTypeLab(rs.getBoolean("PTypeLab"));
        professor.setTypeOrg(rs.getBoolean("PTypeOrg"));
        professor.setUsername(rs.getString("PUsername"));
        professor.setPassword(rs.getString("PPassword"));
        professor.setPersonnelCode(rs.getString("ProfPersonnelCode"));
        professor.setEduGroupId((Long) rs.getObject("ProfEduGroupID"));
        professor.setGrantIssueDate(rs.getTimestamp("ProfGrantIssueDate"));
        professor.setGrantAmount((Long) rs.getObject("ProfGrantAmount"));
        professor.setGrantCredibleUntil(rs.getTimestamp("ProfGrantCredibleUntil"));
        return professor;
    };

    public TUProfessorDAO(JdbcTemplate jdbcTemplate, PersonDAO personDao, EduGroupDAO eduGroupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
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
        template.records = jdbcTemplate.query(template.countQuery("vTUProfessor", "PersonID"), rowMapper);
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
        TUProfessor professor = null;
        try {
            professor = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            if (professor.getEduGroupId() != 0) {
                professor.setEduGroup(eduGroupDao.getById(professor.getEduGroupId()));
            }
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return professor;
    }

    @Override
    public int create(TUProfessor professor) {
        if (professor.getId() != 0) {
            if (professor.getUsername() == null || professor.getUsername() == "") {
                professor.setUsername(professor.getNationalNumber());
                professor.setPassword(professor.getNationalNumber());
            }
            jdbcTemplate.update("UPDATE Person SET PType=?, PUsername=?, PPassword=? WHERE PersonID=?",
                    3, professor.getNationalNumber(), professor.getNationalNumber(), professor.getId());

            return jdbcTemplate.update("INSERT INTO TUProfessor(PersonID, ProfPersonnelCode, ProfEduGroupID, ProfGrantIssueDate, ProfGrantAmount, ProfGrantCredibleUntil) VALUES(?,?,?,?,?,?)",
                    professor.getId(), professor.getPersonnelCode(), professor.getEduGroupId(), professor.getGrantIssueDate(), professor.getGrantAmount(), professor.getGrantCredibleUntil());
        }

        return jdbcTemplate.update("CALL CreateTUProfessor(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", professor.getNationalNumber(), professor.getFirstName(), professor.getLastName(),
                professor.getPhoneNumber(), professor.getEmail(), professor.getGender(), professor.getCustomerId() == 0 ? null : professor.getCustomerId(),
                professor.getNationalNumber(), professor.getNationalNumber(), professor.getPersonnelCode(), professor.getEduGroupId(), professor.getGrantIssueDate(), professor.getGrantAmount(), professor.getGrantCredibleUntil());
    }

    @Override
    public int update(TUProfessor professor) {
        return jdbcTemplate.update("CALL UpdateTUProfessor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", professor.getId(), professor.getNationalNumber(), professor.getFirstName(),
                professor.getLastName(), professor.getPhoneNumber(), professor.getEmail(), professor.getGender(), professor.getCustomerId() == 0 ? null : professor.getCustomerId(),
                professor.getUsername(), professor.getPassword(), professor.getPersonnelCode(), professor.getEduGroupId(), professor.getGrantIssueDate(), professor.getGrantAmount(), professor.getGrantCredibleUntil());
    }

    @Override
    public int delete(Long id) {
//        jdbcTemplate.update("UPDATE Person SET PType=0 WHERE PersonID=?", id);
//        return jdbcTemplate.update("UPDATE TUProfessor SET DDate=CURRENT_TIMESTAMP() WHERE PersonID=?", id);
        return jdbcTemplate.update("CALL DeleteTUProfessor(?)", id);
    }
}