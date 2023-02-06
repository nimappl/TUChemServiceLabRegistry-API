package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Discount;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DiscountDAO implements DAO<Discount> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Discount> rowMapper = (rs, rowNum) -> {
        Discount discount = new Discount();
        discount.setId(rs.getLong("DiscountID"));
        discount.setType(rs.getShort("TDType"));
        discount.setPercent(rs.getShort("TDPercent"));
        discount.setDate(rs.getTimestamp("TDDate"));
        discount.setMinSamples(rs.getShort("TDMinSamples"));
        discount.setName(rs.getString("TDName"));
        return discount;
    };

    public DiscountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Data<Discount> list(Data<Discount> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM discountv";
        String selectQuery = "SELECT * FROM discountv";

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

    public List<Discount> discountsOfTest(long testId, String dName) {
        String query = "SELECT * FROM discountsoftests WHERE TestID=" + testId;
        query += (" AND TDName LIKE '%" + dName + "%'");
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Discount> discountsOfTest(long testId) {
        return discountsOfTest(testId, "");
    }

    public List<Long> getAssociatedTestsForId (long discountId) {
        String sql = "SELECT DISTINCT TestID FROM Test_Discount WHERE DDate IS NULL AND DiscountID=" + discountId;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long testId = rs.getLong("TestID");
            return testId;
        });
    }

    @Override
    public Optional<Discount> getById(Long id) {
        String sql = "SELECT * FROM Discountv WHERE DiscountID = ?";
        Discount discount = null;
        try {
            discount = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.ofNullable(discount);
    }

    @Override
    public int create(Discount discount) {
        return jdbcTemplate.update("INSERT INTO Discount(TDType, TDPercent, TDMinSamples, TDName) VALUES (?,?,?,?)",
                discount.getType(), discount.getPercent(), discount.getMinSamples(), discount.getName());
    }

    @Override
    public int update(Discount discount) {
        return jdbcTemplate.update("CALL UpdateDiscount(?,?,?,?,?,?)", discount.getId(), discount.getType(),
                discount.getPercent(), discount.getDate(), discount.getMinSamples(), discount.getName());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeleteDiscount(?)", id);
    }
}
