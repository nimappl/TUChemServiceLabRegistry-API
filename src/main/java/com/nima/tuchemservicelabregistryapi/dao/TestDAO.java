package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.*;
import com.nima.tuchemservicelabregistryapi.model.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TestDAO implements DAO<Test> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Test> rowMapper = (rs, rowNum) -> {
        Test test = new Test();
        test.setId(rs.getLong("TestID"));
        test.setName(rs.getString("TName"));
        test.setShortName(rs.getString("TShortName"));
        test.setHasPrep(rs.getByte("THasPrep"));
        test.setInstrumentId(rs.getLong("InstrumentID"));
        test.settActive(rs.getByte("TActive"));
        test.setDescription(rs.getString("TDescription"));
        return test;
    };
    private final DiscountDAO discountDao;
    private final TestFeeDAO testFeeDao;
    private final TestPrepDAO testPrepDao;
    private final InstrumentDAO instrumentDao;

    public TestDAO(JdbcTemplate jdbcTemplate, DiscountDAO discountDao, TestFeeDAO testFeeDao, TestPrepDAO testPrepDao, InstrumentDAO instrumentDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountDao = discountDao;
        this.testFeeDao = testFeeDao;
        this.testPrepDao = testPrepDao;
        this.instrumentDao = instrumentDao;
    }

    @Override
    public Data<Test> list(Data<Test> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM testv";
        String selectQuery = "SELECT * FROM testv";

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

        template.records.forEach((Test test) -> {
            test.setInstrument(instrumentDao.getById(test.getInstrumentId()).get());
            test.setDiscounts(discountDao.discountsOfTest(test.getId()));
            test.setFees(testFeeDao.getByTestId(test.getId()));
            test.setSamplePreparations(testPrepDao.getByTestId(test.getId()));
        });

        return template;
    }

    @Override
    public Optional<Test> getById(Long id) {
        String sql = "SELECT * FROM Test WHERE TestID = ?";
        Test test = null;
        try {
            test = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            test.setDiscounts(discountDao.discountsOfTest(test.getId()));
            test.setFees(testFeeDao.getByTestId(test.getId()));
            test.setSamplePreparations(testPrepDao.getByTestId(test.getId()));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(test);
    }

    @Override
    public int create(Test test) {
        long newId = jdbcTemplate.queryForObject("SELECT CreateTest(?,?,?,?,?)",
                new Object[]{test.getName(), test.getShortName(), test.getInstrumentId(), test.gettActive(), test.getDescription()},
                Long.class);

        if (test.getSamplePreparations() != null) test.getSamplePreparations().forEach(prep -> {
            prep.setTestId(newId);
            testPrepDao.create(prep);
        });
        if (test.getFees() != null) test.getFees().forEach(fee -> {
            fee.setTestId(newId);
            testFeeDao.create(fee);
        });
        if (test.getDiscounts() != null) {
            test.getDiscounts().forEach((Discount discount) -> {
//                Optional<Discount> d = discountDao.getById(discount.getId());
//
//                if (d.isEmpty()) discountDao.create(discount);
                jdbcTemplate.update("INSERT INTO Test_Discount(TestID, DiscountID) VALUES (?,?)",
                        newId, discount.getId());
            });
        }
        return 1;
    }

    @Override
    public int update(Test test) {
        // Discounts
        List<Discount> discountsInDB = discountDao.discountsOfTest(test.getId());
        discountsInDB.forEach((Discount discount) -> {
            if (!test.getDiscounts().contains(discount)) { // removed discounts
                jdbcTemplate.update("DELETE FROM Test_Discount WHERE TestID=? AND DiscountID=?",
                        test.getId(), discount.getId());
            }
        });
        if (test.getDiscounts() != null) {
            test.getDiscounts().forEach((Discount discount) -> {
                if (!discountsInDB.contains(discount)) { // new discounts added
                    if (discountDao.getById(discount.getId()).isEmpty())
                        discountDao.create(discount);
                    jdbcTemplate.update("INSERT INTO Test_Discount(TestID, DiscountID) VALUES(?,?)",
                            test.getId(), discount.getId());
                }
            });
        }

        // Fees
        List<TestFee> feesInDB = testFeeDao.getByTestId(test.getId());
        feesInDB.forEach((TestFee feeInDb) -> {
            boolean isRemoved = true;
            for (TestFee fee : test.getFees()) {
                if (feeInDb.getId() == fee.getId()) {
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) testFeeDao.delete(feeInDb.getId());
        });
        if (test.getFees() != null) {
            test.getFees().forEach((TestFee fee) -> {
                boolean isPresentInDb = false;
                for (TestFee feeInDb : feesInDB) {
                    if (fee.getId() == feeInDb.getId()) {
                        isPresentInDb = true;
                        break;
                    }
                }
                if (isPresentInDb) testFeeDao.update(fee);
                else testFeeDao.create(fee);
            });
        }

        // Sample Preps
        List<TestPrep> prepsInDb = testPrepDao.getByTestId(test.getId());
        prepsInDb.forEach((TestPrep prepInDb) -> {
            boolean isRemoved = true;
            for (TestPrep prep : test.getSamplePreparations()) {
                if (prepInDb.getId() == prep.getId()) {
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) testPrepDao.delete(prepInDb.getId());
        });
        if (test.getSamplePreparations() != null) {
            test.getSamplePreparations().forEach((TestPrep prep) -> {
                boolean isPresentInDb = false;
                for (TestPrep prepInDb : prepsInDb) {
                    if (prep.getId() == prepInDb.getId()) {
                        isPresentInDb = true;
                        break;
                    }
                }
                if (isPresentInDb) testPrepDao.update(prep);
                else testPrepDao.create(prep);
            });
        }

        return jdbcTemplate.update("UPDATE Test SET TName=?, TShortName=?, InstrumentID=?, TActive=?, TDescription=? WHERE TestID=?",
                test.getName(), test.getShortName(), test.getInstrumentId(), test.gettActive(), test.getDescription(), test.getId());
    }

    public void toggleStatus(long testId, short status) {
        jdbcTemplate.update("UPDATE Test SET TActive=? WHERE TestID=?", status, testId);
    }

    @Override
    public int delete(Long id) {
        jdbcTemplate.update("UPDATE TestFee SET DDate=CURRENT_TIMESTAMP() WHERE TestID=?", id);
        jdbcTemplate.update("UPDATE TestPrep SET DDate=CURRENT_TIMESTAMP() WHERE TestID=?", id);
        jdbcTemplate.update("DELETE FROM Test_Discount WHERE TestID=?", id);
        return jdbcTemplate.update("CALL DeleteTest(?)", id);
    }
}
