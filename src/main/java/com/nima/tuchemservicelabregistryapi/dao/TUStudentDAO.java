package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.TUStudent;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TUStudentDAO implements DAO<TUStudent> {
    private final JdbcTemplate jdbcTemplate;
    private final EduFieldDAO eduFieldDAO;

    private final RowMapper<TUStudent> rowMapper = (rs, rowNum) -> {
        TUStudent student = new TUStudent();
        student.setId(rs.getLong("PersonID"));
        student.setNationalNumber(rs.getString("PNationalNumber"));
        student.setFirstName(rs.getString("PFirstName"));
        student.setLastName(rs.getString("PLastName"));
        student.setPhoneNumber(rs.getString("PPhoneNumber"));
        student.setEmail(rs.getString("PEmail"));
        student.setGender((Boolean) rs.getObject("PGender"));
        student.setTypeStdn(rs.getBoolean("PTypeStdn"));
        student.setTypeProf(rs.getBoolean("PTypeProf"));
        student.setTypeLab(rs.getBoolean("PTypeLab"));
        student.setTypeOrg(rs.getBoolean("PTypeOrg"));
        student.setStCode(rs.getString("StCode"));
        student.setEduLevel((Short) rs.getObject("StLevel"));
        student.setEduFieldId(rs.getLong("StEduFieldID"));
        return student;
    };

    public TUStudentDAO(JdbcTemplate jdbcTemplate, EduFieldDAO eduFieldDAO) {
        this.jdbcTemplate = jdbcTemplate;
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
        template.filters = new ArrayList<>();

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

    public int updateTable(TUStudent student) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TUStudent WHERE PersonID=" + student.getId(), Integer.class) == 1) {
            jdbcTemplate.update("UPDATE TUStudent SET StCode=?, StLevel=?, StEduFieldID=?, DDate=NULL WHERE PersonID=?",
                    student.getStCode(), student.getEduLevel(), student.getEduFieldId(), student.getId());
        } else {
            jdbcTemplate.update("INSERT INTO TUStudent (PersonID, StCode, StLevel, StEduFieldID) VALUES (?, ?, ?, ?)",
                    student.getId(), student.getStCode(), student.getEduLevel(), student.getEduFieldId());
        }
        return 1;
    }

    @Override
    public int create(TUStudent student) {
        return updateTable(student);
    }

    @Override
    public int update(TUStudent student) {
        return updateTable(student);
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXECUTE DeleteTUStudent ?", id);
    }
}
