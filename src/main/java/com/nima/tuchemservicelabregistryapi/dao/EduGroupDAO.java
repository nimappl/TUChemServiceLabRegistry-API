package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class EduGroupDAO implements DAO<EduGroup> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<EduGroup> rowMapper = (rs, rowNum) -> {
        EduGroup eduGroup = new EduGroup();
        eduGroup.setId(rs.getLong("EduGroupID"));
        eduGroup.setName(rs.getString("EduGroupName"));
        return eduGroup;
    };

    public EduGroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<EduGroup> list(Data<EduGroup> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vEduGroup", "EduGroupID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vEduGroup", "EduGroupID"), rowMapper);
        return template;
    }

    @Override
    public EduGroup getById(Long id) {
        String sql = "SELECT * FROM EduGroup WHERE EduGroupID = ?";
        EduGroup eduGroup = null;
        try {
            eduGroup = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return eduGroup;
    }

    @Override
    public int create(EduGroup eduGroup) {
        return jdbcTemplate.update("INSERT INTO EduGroup(EduGroupName) VALUES(?)", eduGroup.getName());
    }

    @Override
    public int update(EduGroup eduGroup) {
        return jdbcTemplate.update("UPDATE EduGroup SET EduGroupName=? WHERE EduGroupID=?", eduGroup.getName(), eduGroup.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE EduGroup SET DDate=GETDATE() WHERE EduGroupID=?", id);
    }
}
