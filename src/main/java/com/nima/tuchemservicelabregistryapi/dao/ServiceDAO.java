package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceDAO implements DAO<Service> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Service> srvRowMapper = (rs, rowNum) -> {
        Service service = new Service();
        service.setId(rs.getLong("ServiceID"));
        service.setDate(rs.getTimestamp("SDate"));
        service.setSampleQuantity((Integer) rs.getObject("SSampleQuantity"));
        service.setTestTime((Integer) rs.getObject("STestTime"));
        service.setTestId((Long) rs.getObject("TestID"));
        service.setAdditionalCosts((Long) rs.getObject("SAdditionalCosts"));
        service.setTotalPrice((Long) rs.getObject("STotalPrice"));
        service.setTestFeeId((Long) rs.getObject("TestFeeID"));
        service.setServingPersonnelId((Long) rs.getObject("ServingPersonnelID"));
        service.setCustomerAccountId((Long) rs.getObject("CustomerID"));
        service.setConsiderations(rs.getString("SConsiderations"));
        return service;
    };

    private final RowMapper<TService> tSrvRowMapper = (rs, rowNum) -> {
        TService service = new TService();
        service.setId(rs.getLong("ServiceID"));
        service.setTestName(rs.getString("TestName"));
        service.setSampleQuantity((Integer) rs.getObject("SSampleQuantity"));
        service.setCustomerName(rs.getString("CustomerName"));
        service.setDate(rs.getDate("SDate"));
        service.setTotalPrice((Long) rs.getObject("STotalPrice"));
        return service;
    };

    public ServiceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<Service> list(Data<Service> template) {
        return null;
    }

    public Data<TService> tList(Data<TService> options) {
        options.count = jdbcTemplate.queryForObject(options.countQuery("vService", "ServiceID"), Integer.class);
        options.records = jdbcTemplate.query(options.selectQuery("vService", "ServiceID"), tSrvRowMapper);
        return options;
    }

    @Override
    public Service getById(Long id) {
        String sql = "SELECT * FROM Service WHERE ServiceID=" + id;
        Service service = new Service();
        try {
            service = jdbcTemplate.queryForObject(sql, srvRowMapper);
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
        }

        return service;
    }

    @Override
    public int create(Service s) {
        int id;
        id = jdbcTemplate.queryForObject("EXECUTE CreateService ?, ?, ?, ?, ?, ?, ?, ?, ?, ?",
                new Object[]{s.getDate(), s.getSampleQuantity(), s.getTestTime(), s.getTotalPrice(), s.getAdditionalCosts(),
                s.getTestId(), s.getTestFeeId(), s.getServingPersonnelId(), s.getCustomerAccountId(), s.getConsiderations()},
                Integer.class);
        return id;
    }

    @Override
    public int update(Service service) {
        String sql = "UPDATE Service SET SDate=?, SSampleQuantity=?, STestTime=?, STotalPrice=?, SAdditionalCosts=?," +
                " TestID=?, TestFeeID=?, ServingPersonnelID=?, SConsiderations=? WHERE ServiceID=?";
        return jdbcTemplate.update(sql, service.getDate(), service.getSampleQuantity(), service.getTestTime(),
                service.getTotalPrice(), service.getAdditionalCosts(), service.getTestId(), service.getTestFeeId(),
                service.getServingPersonnelId(), service.getConsiderations(), service.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM Service WHERE ServiceID=" + id);
    }
}
