package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.EduField;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EduFieldDAO implements DAO<EduField> {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<EduField> rowMapper = (rs, rowNum) -> {
        EduField eduField = new EduField();
        eduField.setId(rs.getLong("EduFieldID"));
        eduField.setName(rs.getString("EduFieldName"));
        eduField.setEduGroupId(rs.getLong("EduGroupID"));
        return eduField;
    };
    private EduGroupDAO eduGroupDao;

    public EduFieldDAO(JdbcTemplate jdbcTemplate, EduGroupDAO eduGroupDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.eduGroupDao = eduGroupDao;
    }

    @Override
    public Data<EduField> list(Data<EduField> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vEduField", "EduFieldID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vEduField", "EduFieldID"), rowMapper);
        template.records.forEach((EduField eduField) -> {
            if (eduField.getEduGroupId() != null)
                eduField.setEduGroup(eduGroupDao.getById(eduField.getEduGroupId()));
        });
        return template;
    }

    @Override
    public EduField getById(Long id) {
        String sql = "SELECT * FROM EduField WHERE EduFieldID = ?";
        EduField eduField = null;
        try {
            eduField = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            eduField.setEduGroup(eduGroupDao.getById(eduField.getEduGroupId()));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return eduField;
    }

    @Override
    public int create(EduField eduField) {
        return jdbcTemplate.queryForObject("EXECUTE CreateEduField ?, ?",
                new Object[]{eduField.getName(), eduField.getEduGroupId()}, Integer.class);
    }

    @Override
    public int update(EduField eduField) {
        return jdbcTemplate.update("UPDATE EduField SET EduFieldName=?, EduGroupID=? WHERE EduFieldID=?",
                eduField.getName(), eduField.getEduGroupId(), eduField.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE EduField SET DDate=GETDATE() WHERE EduFieldID=?", id);
    }
}
