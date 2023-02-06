package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EduGroupDAO implements DAO<EduGroup> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<EduGroup> rowMapper = (rs, rowNum) -> {
        EduGroup eduGroup = new EduGroup();
        eduGroup.setId(rs.getInt("EduGroupID"));
        eduGroup.setName(rs.getString("EduGroupName"));
        return eduGroup;
    };

    public EduGroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<EduGroup> list(Data<EduGroup> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM edugroupv";
        String selectQuery = "SELECT * FROM edugroupv";

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
    public Optional<EduGroup> getById(Long id) {
        String sql = "SELECT * FROM EduGroup WHERE EduGroupID = ?";
        EduGroup eduGroup = null;
        try {
            eduGroup = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(eduGroup);
    }

    @Override
    public int create(EduGroup eduGroup) {
        return jdbcTemplate.update("INSERT INTO EduGroup(EduGroupName) VALUES(?)", eduGroup.getName());
    }

    @Override
    public int update(EduGroup eduGroup) {
        return jdbcTemplate.update("CALL UpdateEduGroup(?, ?)", eduGroup.getId(), eduGroup.getName());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteEduGroup(?)", id);
    }
}
