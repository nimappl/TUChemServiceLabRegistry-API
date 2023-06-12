package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Account;
import com.nima.tuchemservicelabregistryapi.model.CustomerCandidate;
import com.nima.tuchemservicelabregistryapi.model.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDAO implements DAO<Account>{
    private final JdbcTemplate jdbcTemplate;
    private final PersonGeneralDAO personDAO;
    private final OrganizationDAO organizationDAO;

    private final RowMapper<Account> rowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setId(rs.getLong("AccountID"));
        account.setType((Short) rs.getObject("AccountType"));
        account.setBalance((Long) rs.getObject("AccountBalance"));
        account.setPersonCustomerId((Long) rs.getObject("PersonCustomerID"));
        account.setOrganizationCustomerId((Long) rs.getObject("OrganizationCustomerID"));
        return account;
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
        candidate.setHasContract(rs.getBoolean("HasContract"));
        return candidate;
    };

    public AccountDAO(JdbcTemplate jdbcTemplate, PersonGeneralDAO personDAO, OrganizationDAO organizationDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
        this.organizationDAO = organizationDAO;
    }

    @Override
    public Data<Account> list(Data<Account> template) {
        return null;
    }

    public Account exists(Long customerId, Short customerType) {
        Account account;
        String sql = "SELECT * FROM Account WHERE " +
                     "(PersonCustomerID=" + customerId +
                    " OR OrganizationCustomerID=" + customerId + ") " +
                     "AND AccountType=" + customerType;
        try {
            account = jdbcTemplate.queryForObject(sql, rowMapper);
            if (account.getType() == 1) account.setCustPerson(personDAO.getById(account.getPersonCustomerId()));
            else account.setCustOrganization(organizationDAO.getById(account.getOrganizationCustomerId()));
        } catch (DataAccessException ex) {
            return null;
        }
        return account;
    }

    @Override
    public Account getById(Long id) {
        String sql = "SELECT * FROM Account WHERE AccountID=" + id;
        Account account;
        try {
            account = jdbcTemplate.queryForObject(sql, rowMapper);
            if (account.getType() == 1) account.setCustPerson(personDAO.getById(account.getPersonCustomerId()));
            else account.setCustOrganization(organizationDAO.getById(account.getOrganizationCustomerId()));
        } catch (DataAccessException ex) {
            return null;
        }
        return account;
    }

    public List<CustomerCandidate> getCustomerCandidates(String filter) {
        String sql = "SELECT * FROM vCustomerCandidates " +
                     "WHERE Name LIKE " + "'%" + filter + "%' " +
                     "ORDER BY Name";
        return jdbcTemplate.query(sql, candidateRowMapper);
    }

    public int update(long id, long amount) {
        long currentAmount = jdbcTemplate.queryForObject("SELECT AccountBalance FROM Account WHERE AccountID=" + id, Long.class);
        currentAmount += amount;
        return jdbcTemplate.update("UPDATE Account SET AccountBalance=? WHERE AccountID=?", currentAmount, id);
    }

    @Override
    public int create(Account account) {
        if (account.getType() == 1) {
            account.setPersonCustomerId((long) personDAO.create(account.getCustPerson()));
            account.setOrganizationCustomerId(null);
        }

        if (account.getType() == 2 && account.getCustOrganization().getId() == null) {
            account.setOrganizationCustomerId((long) organizationDAO.create(account.getCustOrganization()));
            account.setPersonCustomerId(null);
        }

        if (account.getBalance() == null) account.setBalance((long) 0);

        return jdbcTemplate.queryForObject("EXECUTE CreateAccount ?, ?, ?, ?",
               new Object[]{account.getType(), account.getBalance(), account.getPersonCustomerId(), account.getOrganizationCustomerId()},
               Integer.class);
    }

    @Override
    public int update(Account account) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
