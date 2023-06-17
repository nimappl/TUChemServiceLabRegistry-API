package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.DiscountDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Discount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountDAO dao;

    public DiscountController(DiscountDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<Discount>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Discount> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/for-test/{testId}")
    public ResponseEntity<List<Discount>> getTestDiscounts(@PathVariable("testId") Long testId) {
        return new ResponseEntity(dao.discountsOfTest(testId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getById(@PathVariable("id") Long id) {
        Discount discount;
        try {
            discount = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Integer> createDiscount(@RequestBody Discount discount) {
        int id = dao.create(discount);
        if (id > 0) return new ResponseEntity<>(id, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateDiscount(@RequestBody Discount discount) {
        if (dao.update(discount) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDiscount(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
