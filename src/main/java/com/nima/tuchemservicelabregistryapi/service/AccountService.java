package com.nima.tuchemservicelabregistryapi.service;

import com.nima.tuchemservicelabregistryapi.dao.AccountDAO;
import com.nima.tuchemservicelabregistryapi.dao.OrganizationDAO;
import com.nima.tuchemservicelabregistryapi.dao.PersonGeneralDAO;
import com.nima.tuchemservicelabregistryapi.model.Account;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.VAccount;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Data<VAccount> list(Data<VAccount> options) {
        Data<VAccount> list = accountDAO.getAll(options);
        list.records.forEach(acc -> {
            if (acc.getType() == 1) acc.setCustPerson(personDAO.getById(acc.getPersonCustomerId()));
            else acc.setCustOrganization(organizationDAO.getById(acc.getOrganizationCustomerId()));
        });
        return list;
    }

    public List<VAccount> getAllOptions(String filter) {
        return accountDAO.getAllOptions(filter);
    }

    public Account exists(Long customerId, Short customerType) {
        return accountDAO.exists(customerId, customerType);
    }

    public Account getById(Long id) {
        return accountDAO.getById(id);
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
