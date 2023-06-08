package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDAO implements DAO<Payment> {
    @Override
    public Data<Payment> list(Data<Payment> template) {
        return null;
    }

    @Override
    public Payment getById(Long id) {
        return null;
    }

    @Override
    public int create(Payment payment) {
        return 0;
    }

    @Override
    public int update(Payment payment) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
