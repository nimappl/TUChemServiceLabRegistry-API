package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.controller.dto.StatusDTO;
import com.nima.tuchemservicelabregistryapi.dao.TestDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/test")
public class TestController {
    private final TestDAO dao;

    public TestController(TestDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<Test>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Test> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getById(@PathVariable("id") Long id) {
        Test test;
        try {
            test = this.dao.getById(id).get();
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createTest(@RequestBody Test test) {
        if (dao.create(test) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PostMapping("/status")
    public ResponseEntity toggleStatus(@RequestBody StatusDTO status) {
        dao.toggleStatus(status.testId, status.status);
        return new ResponseEntity((HttpStatus.OK));
    }

    @PutMapping("/update")
    public ResponseEntity updateTest(@RequestBody Test test) {
        if (dao.update(test) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTest(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}

