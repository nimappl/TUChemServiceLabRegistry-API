package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Discount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscountDAO implements DAO<Discount> {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Discount> rowMapper = (rs, rowNum) -> {
        Discount discount = new Discount();
        discount.setId(rs.getLong("DiscountID"));
        discount.setType(rs.getShort("TDType"));
        discount.setPercent(rs.getShort("TDPercent"));
        discount.setDate(rs.getTimestamp("TDDate"));
        discount.setMinSamples((Short) rs.getObject("TDMinSamples"));
        discount.setName(rs.getString("TDName"));
        return discount;
    };

    public DiscountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Data<Discount> list(Data<Discount> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vDiscount", "DiscountID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vDiscount", "DiscountID"), rowMapper);
        return template;
    }

    public List<Discount> discountsOfTest(long testId, String dName) {
        String query = "SELECT * FROM vDiscountsOfTests WHERE TestID=" + testId;
        query += (" AND TDName LIKE '%" + dName + "%' OR TDName IS NULL");
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
    public Discount getById(Long id) {
        String sql = "SELECT * FROM Discount WHERE DiscountID = ?";
        Discount discount = null;
        try {
            discount = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return discount;
    }

    @Override
    public int create(Discount discount) {
        return jdbcTemplate.update("INSERT INTO Discount(TDType, TDPercent, TDMinSamples, TDName) VALUES (?,?,?,?)",
                discount.getType(), discount.getPercent(), discount.getMinSamples(), discount.getName());
    }

    @Override
    public int update(Discount discount) {
        return jdbcTemplate.update("EXEC UpdateDiscount @id=?, @type=?, @percent=?, @date=?, @minSamples=?, @name=?",
                discount.getId(), discount.getType(), discount.getPercent(), discount.getDate(), discount.getMinSamples(), discount.getName());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXEC DeleteDiscount @id=?", id);
    }
}
