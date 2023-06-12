package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.ServiceDAO;
import com.nima.tuchemservicelabregistryapi.model.CustomerCandidate;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Service;
import com.nima.tuchemservicelabregistryapi.model.TService;
import com.nima.tuchemservicelabregistryapi.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/service")
public class ServiceController {
    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
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

        res = service.tblGetAll(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getById(@PathVariable("id") Long id) {
        Service item;
        try {
            item = this.service.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/query-customer-candidates")
    public ResponseEntity<List<CustomerCandidate>> getCustomerCandidates(@RequestParam(required = false) String filter) {
        List<CustomerCandidate> candidates;
        if (filter != null) {
            candidates = service.getCustomerCandidates(filter);
        } else {
            candidates = service.getCustomerCandidates("");
        }
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createService(@RequestBody Service item) {
        if (service.create(item) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateService(@RequestBody Service item) {
        if (service.update(item) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteService(@PathVariable("id") Long id) {
        if (service.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
