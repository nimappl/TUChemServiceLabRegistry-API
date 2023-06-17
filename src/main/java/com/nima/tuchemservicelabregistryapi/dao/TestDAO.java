package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.*;
import com.nima.tuchemservicelabregistryapi.model.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDAO implements DAO<Test> {
    private final JdbcTemplate jdbcTemplate;
    private final DiscountDAO discountDao;
    private final TestFeeDAO testFeeDao;
    private final TestPrepDAO testPrepDao;
    private final InstrumentDAO instrumentDao;

    private final RowMapper<Test> rowMapper = (rs, rowNum) -> {
        Test test = new Test();
        test.setId(rs.getLong("TestID"));
        test.setName(rs.getString("TName"));
        test.setShortName(rs.getString("TShortName"));
        test.setHasPrep(rs.getByte("THasPrep"));
        test.setInstrumentId(rs.getLong("InstrumentID"));
        test.settActive(rs.getBoolean("TActive"));
        test.setDescription(rs.getString("TDescription"));
        return test;
    };

    public TestDAO(JdbcTemplate jdbcTemplate, DiscountDAO discountDao, TestFeeDAO testFeeDao, TestPrepDAO testPrepDao, InstrumentDAO instrumentDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountDao = discountDao;
        this.testFeeDao = testFeeDao;
        this.testPrepDao = testPrepDao;
        this.instrumentDao = instrumentDao;
    }

    @Override
    public Data<Test> list(Data<Test> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vTest", "TestID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vTest", "TestID"), rowMapper);
        template.records.forEach((Test test) -> {
            test.setInstrument(instrumentDao.getById(test.getInstrumentId()));
            test.setDiscounts(discountDao.discountsOfTest(test.getId()));
            test.setFees(testFeeDao.getByTestId(test.getId()));
            test.setSamplePreparations(testPrepDao.getByTestId(test.getId()));
        });

        return template;
    }

    @Override
    public Test getById(Long id) {
        String sql = "SELECT * FROM Test WHERE TestID = ?";
        Test test = new Test();
        try {
            test = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            test.setDiscounts(discountDao.discountsOfTest(test.getId()));
            test.setFees(testFeeDao.getByTestId(test.getId()));
            test.setSamplePreparations(testPrepDao.getByTestId(test.getId()));
            test.setInstrument(instrumentDao.getById(test.getInstrumentId()));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return test;
    }

    @Override
    public int create(Test test) {
        long newTestId = jdbcTemplate.queryForObject("EXEC CreateTest @name=?, @shortName=?, @hasPrep=?, @instId=?, @active=?, @description=?",
                new Object[]{test.getName(), test.getShortName(), test.getHasPrep(), test.getInstrumentId(), test.gettActive(), test.getDescription()},
                Long.class);

        test.getSamplePreparations().forEach(prep -> {
            prep.setTestId(newTestId);
            testPrepDao.create(prep);
        });
        test.getFees().forEach(fee -> {
            fee.setTestId(newTestId);
            testFeeDao.create(fee);
        });
        test.getDiscounts().forEach((Discount discount) -> {
            jdbcTemplate.update("INSERT INTO Test_Discount(TestID, DiscountID) VALUES (?,?)", newTestId, discount.getId());
        });
        return 1;
    }

    @Override
    public int update(Test test) {
        List<Discount> discountsInDB = discountDao.discountsOfTest(test.getId());
        List<TestFee> feesInDB = testFeeDao.getByTestId(test.getId());
        List<TestPrep> prepsInDb = testPrepDao.getByTestId(test.getId());

        // Discounts
        discountsInDB.forEach((Discount discount) -> {
            if (!test.getDiscounts().contains(discount)) { // removed discounts
                jdbcTemplate.update("DELETE FROM Test_Discount WHERE TestID=? AND DiscountID=?",
                        test.getId(), discount.getId());
            }
        });
        if (test.getDiscounts() != null) {
            test.getDiscounts().forEach((Discount discount) -> {
                if (!discountsInDB.contains(discount)) { // new discounts
                    jdbcTemplate.update("INSERT INTO Test_Discount(TestID, DiscountID) VALUES(?,?)",
                            test.getId(), discount.getId());
                }
            });
        }

        // Fees
        feesInDB.forEach((TestFee feeInDb) -> {
            boolean isRemoved = true;
            for (TestFee fee : test.getFees()) {
                if (feeInDb.getId().equals(fee.getId())) {
                    if (!feeInDb.isEqualTo(fee))
                        testFeeDao.update(fee);
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) testFeeDao.delete(feeInDb.getId());
        });
        test.getFees().forEach((TestFee fee) -> {
            if (fee.getId() == null) testFeeDao.create(fee);
        });

        // Sample Preps
        prepsInDb.forEach((TestPrep prepInDb) -> {
            boolean isRemoved = true;
            for (TestPrep prep : test.getSamplePreparations()) {
                if (prepInDb.getId() == prep.getId()) {
                    if (!prepInDb.isEqualTo(prep))
                        testPrepDao.update(prep);
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) testPrepDao.delete(prepInDb.getId());
        });
        test.getSamplePreparations().forEach((TestPrep prep) -> {
            if (prep.getId() == null) testPrepDao.create(prep);
        });

        return jdbcTemplate.update("UPDATE Test SET TName=?, TShortName=?, InstrumentID=?, TActive=?, TDescription=? WHERE TestID=?",
                test.getName(), test.getShortName(), test.getInstrumentId(), test.gettActive(), test.getDescription(), test.getId());
    }

    public void toggleStatus(long testId, short status) {
        jdbcTemplate.update("UPDATE Test SET TActive=? WHERE TestID=?", status, testId);
    }

    @Override
    public int delete(Long id) {
        jdbcTemplate.update("UPDATE TestFee SET DDate=GETDATE() WHERE TestID=?", id);
        jdbcTemplate.update("UPDATE TestPrep SET DDate=GETDATE() WHERE TestID=?", id);
        jdbcTemplate.update("DELETE FROM Test_Discount WHERE TestID=?", id);
        return jdbcTemplate.update("UPDATE Test SET DDate=GETDATE() WHERE TestID=?", id);
    }
}
