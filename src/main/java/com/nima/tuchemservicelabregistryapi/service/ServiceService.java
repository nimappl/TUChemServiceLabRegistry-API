package com.nima.tuchemservicelabregistryapi.service;

import com.nima.tuchemservicelabregistryapi.dao.*;
import com.nima.tuchemservicelabregistryapi.model.CustomerCandidate;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Service;
import com.nima.tuchemservicelabregistryapi.model.TService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceService {
    private final ServiceDAO serviceDAO;
    private final AccountDAO accountDAO;
    private final TestDAO testDAO;
    private final DiscountDAO discountDAO;
    private final ServiceResultsFileDAO resultsFileDAO;
    private final LabPersonnelDAO labPersonnelDAO;
    private final TestFeeDAO testFeeDAO;
    private final TestPrepDAO testPrepDAO;

    public ServiceService(ServiceDAO serviceDAO, AccountDAO accountDAO, TestDAO testDAO, DiscountDAO discountDAO, ServiceResultsFileDAO resultsFileDAO, LabPersonnelDAO labPersonnelDAO, TestFeeDAO testFeeDAO, TestPrepDAO testPrepDAO) {
        this.serviceDAO = serviceDAO;
        this.accountDAO = accountDAO;
        this.testDAO = testDAO;
        this.discountDAO = discountDAO;
        this.resultsFileDAO = resultsFileDAO;
        this.labPersonnelDAO = labPersonnelDAO;
        this.testFeeDAO = testFeeDAO;
        this.testPrepDAO = testPrepDAO;
    }

    public Data<TService> tblGetAll(Data<TService> options) {
        return serviceDAO.tList(options);
    }

    public List<Service> servicesOfAccount() {
        return new ArrayList<>();
    }

    public List<CustomerCandidate> getCustomerCandidates(String filter) {
        return accountDAO.getCustomerCandidates(filter);
    }

    public Service getById(Long id) {
        Service service = serviceDAO.getById(id);
        service.setServingPersonnel(labPersonnelDAO.getById(service.getServingPersonnelId()));
        service.setTest(testDAO.getById(service.getTestId()));
        service.setTestFee(testFeeDAO.getById(service.getTestFeeId()));
        service.setTestPreps(testPrepDAO.getByServiceId(service.getId()));
        service.setResultFiles(resultsFileDAO.getFilesOfService(id));
        service.setCustomerAccount(accountDAO.getById(service.getCustomerAccountId()));
        service.setDiscounts(discountDAO.discountsOfService(service.getId()));
        return service;
    }

    public int create(Service item) {
        if (item.getCustomerAccount().getId() == null)
            item.setCustomerAccountId((long) accountDAO.create(item.getCustomerAccount()));
        else {
            item.setCustomerAccountId(item.getCustomerAccount().getId());
        }
        accountDAO.update(item.getCustomerAccountId(), item.getTotalPrice());

        item.setId((long) serviceDAO.create(item));
        item.getTestPreps().forEach(prep -> {
            testPrepDAO.addForService(prep.getId(), item.getId());
        });
        item.getDiscounts().forEach(discount -> {
            if (discount.getId() == null) discount.setId((long) discountDAO.create(discount));
            discountDAO.addForService(discount.getId(), item.getId());
        });
        return 1;
    }

    public int update(Service item) {
        Service itemInDb = serviceDAO.getById(item.getId());
        testPrepDAO.removeForService(item.getId());
        discountDAO.removeForService(item.getId());
        item.getTestPreps().forEach(prep -> {
            testPrepDAO.addForService(prep.getId(), item.getId());
        });
        item.getDiscounts().forEach(discount -> {
            if (discount.getId() == null) discount.setId((long) discountDAO.create(discount));
            discountDAO.addForService(discount.getId(), item.getId());
        });
        accountDAO.update(item.getCustomerAccountId(), item.getTotalPrice() - itemInDb.getTotalPrice());
        serviceDAO.update(item);
        return 1;
    }

    public int delete(Long id) {
        discountDAO.removeForService(id);
        testPrepDAO.removeForService(id);
        return serviceDAO.delete(id);
    }
}
