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
    private final TestDAO testDAO;
    private final DiscountDAO discountDAO;
    private final LabPersonnelDAO labPersonnelDAO;
    private final TestFeeDAO testFeeDAO;
    private final TestPrepDAO testPrepDAO;

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

    private final RowMapper<CustomerCandidate> candidateRowMapper = (rs, rowNum) -> {
        CustomerCandidate candidate = new CustomerCandidate();
        candidate.setID(rs.getLong("ID"));
        candidate.setType((Integer) rs.getObject("Type"));
        candidate.setName(rs.getString("Name"));
        candidate.setTypeStdn(rs.getBoolean("PTypeStdn"));
        candidate.setTypeProf(rs.getBoolean("PTypeProf"));
        candidate.setTypeOrg(rs.getBoolean("PTypeOrg"));
        candidate.setStdnEduGroup(rs.getString("StdnEduGroup"));
        candidate.setProfEduGroup(rs.getString("ProfEduGroup"));
        return candidate;
    };

    private final RowMapper<ServiceResultFile> rsFileRowMapper = (rs, rowNum) -> {
        ServiceResultFile resultFile = new ServiceResultFile();
        resultFile.setFileName(rs.getString("FileName"));
        resultFile.setFilePath(rs.getString("FilePath"));
        return resultFile;
    };

    public ServiceDAO(JdbcTemplate jdbcTemplate, TestDAO testDAO, DiscountDAO discountDAO, LabPersonnelDAO labPersonnelDAO, TestFeeDAO testFeeDAO, TestPrepDAO testPrepDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.testDAO = testDAO;
        this.discountDAO = discountDAO;
        this.labPersonnelDAO = labPersonnelDAO;
        this.testFeeDAO = testFeeDAO;
        this.testPrepDAO = testPrepDAO;
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

        service.setServingPersonnel(labPersonnelDAO.getById(service.getServingPersonnelId()));
        service.setTest(testDAO.getById(service.getTestId()));
        service.setTestFee(testFeeDAO.getById(service.getTestFeeId()));
        service.setTestPreps(testPrepDAO.getByServiceId(service.getId()));
        String serviceRSFileSql = "SELECT * FROM ServiceResultFile WHERE ServiceID=" + service.getId();
        service.setResultFiles(jdbcTemplate.query(serviceRSFileSql , rsFileRowMapper));

        return service;
    }

    public List<CustomerCandidate> getCustomerCandidates(String filter) {
        String sql = "SELECT * FROM vCustomerCandidates " +
                     "WHERE Name LIKE" + "'%" + filter + "%'" +
                     "ORDER BY Name";
        return jdbcTemplate.query(sql, candidateRowMapper);
    }

    @Override
    public int create(Service service) {
        return 0;
    }

    @Override
    public int update(Service service) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
