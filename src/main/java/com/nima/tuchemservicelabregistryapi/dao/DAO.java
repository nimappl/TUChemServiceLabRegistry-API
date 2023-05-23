package com.nima.tuchemservicelabregistryapi.dao;
import com.nima.tuchemservicelabregistryapi.model.Data;
import java.util.Optional;

public interface DAO<T> {
    Data<T> list(Data<T> template);
    T getById(Long id);
    int create(T t);
    int update(T t);
    int delete(Long id);
}
