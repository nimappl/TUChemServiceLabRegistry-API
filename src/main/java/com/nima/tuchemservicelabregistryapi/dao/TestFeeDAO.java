package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.TestFee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TestFeeDAO implements DAO<TestFee> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<TestFee> rowMapper = (rs, rowNum) -> {
        TestFee fee = new TestFee();
        fee.setId(rs.getLong("TFeeID"));
        fee.setAmount(rs.getDouble("TFAmount"));
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
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM testfeev";
        String selectQuery = "SELECT * FROM testfeev";

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

    public List<TestFee> getByTestId(long testId) {
        String query = "SELECT * FROM TestFee WHERE DDate IS NULL AND TestID=" + testId;
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Optional<TestFee> getById(Long id) {
        String sql = "SELECT * FROM TestFee WHERE TFeeID = ?";
        TestFee fee = null;
        try {
            fee = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(fee);
    }

    @Override
    public int create(TestFee fee) {
        return jdbcTemplate.update("INSERT INTO TestFee(TFBase, TFAmount, TestID, TFStep) VALUES (?,?,?,?)",
                fee.getType(), fee.getAmount(), fee.getTestId(), fee.getStep());
    }

    @Override
    public int update(TestFee fee) {
        return jdbcTemplate.update("CALL UpdateTestFee(?,?,?,?,?,?)",
                fee.getId(), fee.getType(), fee.getAmount(), fee.getTestId(), fee.getDate(), fee.getStep());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteTestFee(?)", id);
    }
}
