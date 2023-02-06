package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.TestFee;
import com.nima.tuchemservicelabregistryapi.model.TestPrep;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TestPrepDAO implements DAO<TestPrep> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<TestPrep> rowMapper = (rs, rowNum) -> {
        TestPrep testPrep = new TestPrep();
        testPrep.setId(rs.getLong("TestPrepID"));
        testPrep.setTestId(rs.getLong("TestID"));
        testPrep.setType(rs.getString("TPrepType"));
        testPrep.setPrice(rs.getDouble("TPrepPrice"));
        return testPrep;
    };

    public TestPrepDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<TestPrep> list(Data<TestPrep> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM testprepv";
        String selectQuery = "SELECT * FROM testprepv";

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

    public List<TestPrep> getByTestId(long testId) {
        String query = "SELECT * FROM TestPrep WHERE DDate IS NULL AND TestID=" + testId;
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Optional<TestPrep> getById(Long id) {
        String sql = "SELECT * FROM TestPrep WHERE TestPrepID = ?";
        TestPrep testPrep = null;
        try {
            testPrep = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(testPrep);
    }

    @Override
    public int create(TestPrep testPrep) {
        return jdbcTemplate.update("INSERT INTO TestPrep(TestId, TPrepType, TPrepPrice) VALUES (?,?,?)",
                testPrep.getTestId(), testPrep.getType(), testPrep.getPrice());
    }

    @Override
    public int update(TestPrep testPrep) {
        return jdbcTemplate.update("CALL UpdateTestPrep(?,?,?,?)",
                testPrep.getId(), testPrep.getTestId(), testPrep.getType(), testPrep.getPrice());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteTestPrep(?)", id);
    }
}
