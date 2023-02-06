package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.EduField;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EduFieldDAO implements DAO<EduField> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<EduField> rowMapper = (rs, rowNum) -> {
        EduField eduField = new EduField();
        eduField.setId(rs.getInt("EduFieldID"));
        eduField.setName(rs.getString("EduFieldName"));
        eduField.setFieldLevel(rs.getShort("EduFieldLevel"));
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
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM edufieldv";
        String selectQuery = "SELECT * FROM edufieldv";

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

        template.records.forEach((EduField eduField) -> {
            if (eduField.getEduGroupId() != 0)
                eduField.setEduGroup(eduGroupDao.getById(eduField.getEduGroupId()).get());
        });
        return template;
    }

    @Override
    public Optional<EduField> getById(Long id) {
        String sql = "SELECT * FROM EduField WHERE EduFieldID = ?";
        EduField eduField = null;
        try {
            eduField = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            eduField.setEduGroup(eduGroupDao.getById(eduField.getEduGroupId()).get());
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(eduField);
    }

    @Override
    public int create(EduField eduField) {
        return jdbcTemplate.update("INSERT INTO EduField(EduFieldName, EduFieldLevel, EduGroupID) VALUES(?, ?, ?)",
                eduField.getName(), eduField.getFieldLevel(), eduField.getEduGroupId());
    }

    @Override
    public int update(EduField eduField) {
        return jdbcTemplate.update("CALL UpdateEduField(?, ?, ?, ?)",
                eduField.getId(), eduField.getName(), eduField.getFieldLevel(), eduField.getEduGroupId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteEduField(?)", id);
    }
}
