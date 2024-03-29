package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Payment;
import com.nima.tuchemservicelabregistryapi.model.TPayment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentDAO implements DAO<Payment> {
    private final JdbcTemplate jdbcTemplate;
    private final AccountDAO accountDAO;
    private final TUProfessorDAO professorDAO;

    private final RowMapper<TPayment> tRowMapper = (rs, roNum) -> {
        TPayment payment = new TPayment();
        payment.setId(rs.getLong("PaymentID"));
        payment.setType((Short) rs.getObject("PmntType"));
        payment.setDate(rs.getDate("PmntDate"));
        payment.setAmount((Long) rs.getObject("PmntAmount"));
        payment.setCustomerName(rs.getString("CustomerName"));
        return payment;
    };

    private final RowMapper<Payment> rowMapper = (rs, rowNum) -> {
        Payment payment = new Payment();
        payment.setId(rs.getLong("PaymentID"));
        payment.setDate(rs.getTimestamp("PmntDate"));
        payment.setAmount(rs.getLong("PmntAmount"));
        payment.setType(rs.getShort("PmntType"));
        payment.setAccountId(rs.getLong("AccountID"));
        payment.setCashBasisType((Short) rs.getObject("CPmntType"));
        payment.setCashBasisTrackingNo(rs.getString("CPmntTrackingNo"));
        payment.setLabsnetCreditTitle(rs.getString("PLNCreditTitle"));
        payment.setLabsnetTransactionCode(rs.getString("PLNTransactionCode"));
        payment.setGrantProfessorId((Long) rs.getObject("ProfessorID"));
        return payment;
    };

    public PaymentDAO(JdbcTemplate jdbcTemplate, AccountDAO accountDAO, TUProfessorDAO professorDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDAO = accountDAO;
        this.professorDAO = professorDAO;
    }

    @Override
    public Data<Payment> list(Data<Payment> template) {
        return null;
    }

    public Data<TPayment> getAll(Data<TPayment> options) {
        options.count = jdbcTemplate.queryForObject(options.countQuery("vPayment2", "PaymentID"), Integer.class);
        options.records = jdbcTemplate.query(options.selectQuery("vPayment2", "PaymentID"), tRowMapper);
        return options;
    }

    @Override
    public Payment getById(Long id) {
        String sql = "SELECT * From vPayment WHERE PaymentID=" + id;
        Payment payment;
        try {
            payment = jdbcTemplate.queryForObject(sql, rowMapper);
            if (payment.getType() == 1) payment.setGrantProfessor(professorDAO.getById(payment.getGrantProfessorId()));
            payment.setAccount(accountDAO.getById(payment.getAccountId()));
        } catch (DataAccessException ex) {
            return null;
        }
        return payment;
    }

    @Override
    public int create(Payment payment) {
        int id = jdbcTemplate.queryForObject("EXECUTE CreatePayment ?, ?, ?, ?",
                new Object[]{payment.getDate(), payment.getAmount(), payment.getType(), payment.getAccountId()}, Integer.class);

        if (payment.getType() == 0) {
            jdbcTemplate.update("INSERT INTO PaymentCashBasis (PaymentID, CPmntType, CPmntTrackingNo) VALUES (?, ?, ?)",
                    id, payment.getCashBasisType(), payment.getCashBasisTrackingNo());
        } else if (payment.getType() == 1) {
            jdbcTemplate.update("INSERT INTO PaymentTUProfGrant (PaymentID, ProfessorID) VALUES (?, ?)",
                    id, payment.getGrantProfessorId());
            professorDAO.updateGrantBalance(payment.getGrantProfessorId(), payment.getAmount());
        } else {
            jdbcTemplate.update("INSERT INTO PaymentLabsnetCredit (PaymentID, PLNCreditTitle, PLNTransactionCode) VALUES (?, ?, ?)",
                    id, payment.getLabsnetCreditTitle(), payment.getLabsnetTransactionCode());
        }

        accountDAO.update(payment.getAccountId(), payment.getAmount() * -1);
        return 1;
    }

    @Override
    public int update(Payment payment) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        Payment pmnt = getById(id);
        accountDAO.update(pmnt.getAccountId(), pmnt.getAmount());
        return jdbcTemplate.update("EXECUTE DeletePayment " + id);
    }
}
