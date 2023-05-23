package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.TUStudent;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TUStudentDAO implements DAO<TUStudent> {
    private JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    private final EduFieldDAO eduFieldDAO;

    private RowMapper<TUStudent> rowMapper = (rs, rowNum) -> {
        TUStudent student = new TUStudent();
        student.setId(rs.getLong("PersonID"));
        student.setNationalNumber(rs.getString("PNationalNumber"));
        student.setFirstName(rs.getString("PFirstName"));
        student.setLastName(rs.getString("PLastName"));
        student.setPhoneNumber(rs.getString("PPhoneNumber"));
        student.setEmail(rs.getString("PEmail"));
        student.setGender((Boolean) rs.getObject("PGender"));
        student.setCustomerId((Long) rs.getObject("CustomerID"));
        student.setTypeStdn(rs.getBoolean("PTypeStdn"));
        student.setTypeProf(rs.getBoolean("PTypeProf"));
        student.setTypeLab(rs.getBoolean("PTypeLab"));
        student.setTypeOrg(rs.getBoolean("PTypeOrg"));
        student.setUsername(rs.getString("PUsername"));
        student.setPassword(rs.getString("PPassword"));
        student.setStCode(rs.getString("StCode"));
        student.setLevel((Short) rs.getObject("StLevel"));
        student.setEduFieldId(rs.getLong("StEduFieldID"));
        return student;
    };

    public TUStudentDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO, EduFieldDAO eduFieldDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
        this.eduFieldDAO = eduFieldDAO;
    }

    @Override
    public Data<TUStudent> list(Data<TUStudent> template) {
        template.filters.add(new Filter("PTypeStdn", "1"));
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTUStudent", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTUStudent", "PersonID"), rowMapper);
        template.records.forEach((TUStudent student) -> {
            if (student.getEduFieldId() != 0) {
                student.setEduField(eduFieldDAO.getById(student.getEduFieldId()));
            }
        });

        return template;
    }

    @Override
    public TUStudent getById(Long id) {
        String sql = "SELECT * FROM vTUStudentAll WHERE PersonID=?";
        TUStudent student = null;
        try {
            student = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            if (student.getEduFieldId() != null)
                student.setEduField(eduFieldDAO.getById(student.getEduFieldId()));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return student;
    }

    @Override
    public int create(TUStudent student) {
        int id;
        if (student.getId() == null) {
            id = personDAO.create(student.asPerson());
        } else {
            id = (int)((long) student.getId());
        }

        jdbcTemplate.update("UPDATE Person SET PTypeStdn=1 WHERE PersonID=?", id);
        jdbcTemplate.update("INSERT INTO TUStudent (PersonID, StCode, StLevel, StEduFieldID) VALUES (?, ?, ?, ?)",
                id, student.getStCode(), student.getLevel(), student.getEduFieldId());
        return 1;
    }

    @Override
    public int update(TUStudent student) {
        personDAO.update(student.asPerson());
        return jdbcTemplate.update("UPDATE TUStudent SET StCode=?, StLevel=?, StEduFieldID=? WHERE PersonID=?",
                student.getStCode(), student.getLevel(), student.getEduFieldId(), student.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXECUTE DeleteTUStudent ?", id);
    }
}
