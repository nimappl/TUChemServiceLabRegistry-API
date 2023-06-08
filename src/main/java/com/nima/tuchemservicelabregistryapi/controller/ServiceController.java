package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.ServiceDAO;
import com.nima.tuchemservicelabregistryapi.model.CustomerCandidate;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Service;
import com.nima.tuchemservicelabregistryapi.model.TService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceDAO dao;

    public ServiceController(ServiceDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<TService>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<TService> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.tList(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getById(@PathVariable("id") Long id) {
        Service service;
        try {
            service = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @GetMapping("/query-customer-candidates")
    public ResponseEntity<List<CustomerCandidate>> getCustomerCandidates(@RequestParam(required = false) String filter) {
        List<CustomerCandidate> candidates;
        if (filter != null) {
            candidates = dao.getCustomerCandidates(filter);
        } else {
            candidates = dao.getCustomerCandidates("");
        }
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createService(@RequestBody Service service) {
        if (dao.create(service) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateService(@RequestBody Service service) {
        if (dao.update(service) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteService(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
