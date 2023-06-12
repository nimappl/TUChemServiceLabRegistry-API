package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TestPrep;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestPrepDAO implements DAO<TestPrep> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<TestPrep> rowMapper = (rs, rowNum) -> {
        TestPrep testPrep = new TestPrep();
        testPrep.setId(rs.getLong("TestPrepID"));
        testPrep.setTestId(rs.getLong("TestID"));
        testPrep.setType(rs.getString("TPrepType"));
        testPrep.setPrice(rs.getLong("TPrepPrice"));
        return testPrep;
    };

    public TestPrepDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<TestPrep> list(Data<TestPrep> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTestPerp", "TestPrepID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTestPrep", "TestPrepID"), rowMapper);
        return template;
    }

    public List<TestPrep> getByTestId(Long testId) {
        String sql = "SELECT * FROM TestPrep WHERE DDate IS NULL AND TestID=" + testId;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TestPrep> getByServiceId(Long serviceId) {
        String sql = "SELECT * FROM vTestPrepsOfServices WHERE ServiceID=" + serviceId;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public TestPrep getById(Long id) {
        String sql = "SELECT * FROM TestPrep WHERE TestPrepID = ?";
        TestPrep testPrep = null;
        try {
            testPrep = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return testPrep;
    }

    public void addForService(long prepId, long serviceId) {
        jdbcTemplate.update("INSERT INTO Service_TestPrep (ServiceID, TestPrepID) VALUES (?, ?)",
                serviceId, prepId);
    }

    public void removeForService(long serviceId) {
        jdbcTemplate.update("DELETE FROM Service_TestPrep WHERE ServiceID=" + serviceId);
    }

    @Override
    public int create(TestPrep testPrep) {
        return jdbcTemplate.update("INSERT INTO TestPrep (TestID, TPrepType, TPrepPrice) VALUES (?,?,?)",
                testPrep.getTestId(), testPrep.getType(), testPrep.getPrice());
    }

    @Override
    public int update(TestPrep testPrep) {
        return jdbcTemplate.update("EXEC UpdateTestPrep @id=?, @testId=?, @type=?, @price=?",
                testPrep.getId(), testPrep.getTestId(), testPrep.getType(), testPrep.getPrice());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE TestPrep SET DDate=GETDATE() WHERE TestPrepID=?", id);
    }
}
