package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.ServiceResultFile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceResultsFileDAO implements DAO<ServiceResultFile>{
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceResultFile> rowMapper = (rs, rowNum) -> {
        ServiceResultFile resultFile = new ServiceResultFile();
        resultFile.setFileName(rs.getString("FileName"));
        resultFile.setFilePath(rs.getString("FilePath"));
        return resultFile;
    };

    public ServiceResultsFileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<ServiceResultFile> list(Data<ServiceResultFile> template) {
        return null;
    }

    public List<ServiceResultFile> getFilesOfService(Long serviceId) {
        String sql = "SELECT * FROM ServiceResultFile WHERE ServiceID=" + serviceId;
        return jdbcTemplate.query(sql , rowMapper);
    }

    @Override
    public ServiceResultFile getById(Long id) {
        return null;
    }

    @Override
    public int create(ServiceResultFile serviceResultFile) {
        return 0;
    }

    @Override
    public int update(ServiceResultFile serviceResultFile) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
