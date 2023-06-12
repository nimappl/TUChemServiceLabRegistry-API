package com.nima.tuchemservicelabregistryapi.service;

import com.nima.tuchemservicelabregistryapi.dao.AccountDAO;
import com.nima.tuchemservicelabregistryapi.dao.OrganizationDAO;
import com.nima.tuchemservicelabregistryapi.dao.PersonGeneralDAO;
import com.nima.tuchemservicelabregistryapi.model.Account;
import com.nima.tuchemservicelabregistryapi.model.Data;
import org.springframework.stereotype.Component;

@Component
public class AccountService {
    private final AccountDAO accountDAO;
    private final PersonGeneralDAO personDAO;
    private final OrganizationDAO organizationDAO;

    public AccountService(AccountDAO accountDAO, PersonGeneralDAO personDAO, OrganizationDAO organizationDAO) {
        this.accountDAO = accountDAO;
        this.personDAO = personDAO;
        this.organizationDAO = organizationDAO;
    }

    public Data<Account> list(Data<Account> template) {
        return null;
    }

    public Account exists(Long customerId, Short customerType) {
        Account account = accountDAO.exists(customerId, customerType);
        if (account != null) {
            if (account.getType() == 1) account.setCustPerson(personDAO.getById(account.getPersonCustomerId()));
            else account.setCustOrganization(organizationDAO.getById(account.getOrganizationCustomerId()));
        }
        return account;
    }

    public Account getById(Long id) {
        return null;
    }

    public int updateBalance(long id, long amount) {
        return accountDAO.update(id, amount);
    }

    public int create(Account account) {
        return 0;
    }

    public int update(Account account) {
        return 0;
    }

    public int delete(Long id) {
        return 0;
    }
}
