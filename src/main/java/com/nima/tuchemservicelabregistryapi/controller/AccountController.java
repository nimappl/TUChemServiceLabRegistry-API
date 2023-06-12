package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.service.AccountService;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Data<Account>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Account> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = service.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/exists/{custId}/{custType}")
    public ResponseEntity<Account> exists(@PathVariable("custId") Long id, @PathVariable("custType") Short type) {
        Account account = service.exists(id, type);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/update-balance/{accountId}/{amount}")
    public ResponseEntity updateBalance(@PathVariable("accountId") Long accountId, @PathVariable("amount") Long amount) {
        if (service.updateBalance(accountId, amount) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable("id") Long id) {
        Account account;
        try {
            account = this.service.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createEduGroup(@RequestBody Account account) {
        if (service.create(account) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updateEduGroup(@RequestBody Account account) {
        if (service.update(account) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEduGroup(@PathVariable("id") Long id) {
        if (service.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}
