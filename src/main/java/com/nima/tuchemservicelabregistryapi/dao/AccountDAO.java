package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Account;
import com.nima.tuchemservicelabregistryapi.model.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO implements DAO<Account>{
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Account> rowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setId(rs.getLong("AccountID"));
        account.setType((Short) rs.getObject("AccountType"));
        account.setBalance((Long) rs.getObject("AccountBalance"));
        account.setPersonCustomerId((Long) rs.getObject("PersonCustomerID"));
        account.setOrganizationCustomerId((Long) rs.getObject("OrganizationCustomerID"));
        return account;
    };

    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<Account> list(Data<Account> template) {
        return null;
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Override
    public int create(Account account) {
        return 0;
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
