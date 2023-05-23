package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TestFee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestFeeDAO implements DAO<TestFee> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<TestFee> rowMapper = (rs, rowNum) -> {
        TestFee fee = new TestFee();
        fee.setId(rs.getLong("TFeeID"));
        fee.setAmount(rs.getLong("TFAmount"));
        fee.setDate(rs.getTimestamp("TFDate"));
        fee.setStep(rs.getShort("TFStep"));
        fee.setTestId(rs.getLong("TestID"));
        fee.setType(rs.getShort("TFBase"));
        return fee;
    };

    public TestFeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<TestFee> list(Data<TestFee> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTestFee", "TFeeID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTestFee", "TFeeID"), rowMapper);
        return template;
    }

    public List<TestFee> getByTestId(Long testId) {
        String query = "SELECT * FROM TestFee WHERE DDate IS NULL AND TestID=" + testId;
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public TestFee getById(Long id) {
        String sql = "SELECT * FROM TestFee WHERE TFeeID = ?";
        TestFee fee = null;
        try {
            fee = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return fee;
    }

    @Override
    public int create(TestFee fee) {
        return jdbcTemplate.update("INSERT INTO TestFee(TFBase, TFAmount, TestID, TFStep) VALUES (?,?,?,?)",
                fee.getType(), fee.getAmount(), fee.getTestId(), fee.getStep());
    }

    @Override
    public int update(TestFee fee) {
        return jdbcTemplate.update("EXEC UpdateTestFee @id=?, @base=?, @amount=?, @testId=?, @date=?, @step=?",
                fee.getId(), fee.getType(), fee.getAmount(), fee.getTestId(), fee.getDate(), fee.getStep());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE TestFee SET DDate=GETDATE() WHERE TFeeID=?", id);
    }
}
