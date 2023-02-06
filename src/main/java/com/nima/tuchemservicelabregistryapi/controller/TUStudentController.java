package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.TUStudentDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TUStudent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/student")
public class TUStudentController {
    private final TUStudentDAO dao;

    public TUStudentController(TUStudentDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<TUStudent>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<TUStudent> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TUStudent> getById(@PathVariable("id") Long id) {
        TUStudent person;
        try {
            person = this.dao.getById(id).get();
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createPersonnel(@RequestBody TUStudent person) {
        if (dao.create(person) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updatePersonnel(@RequestBody TUStudent person) {
        if (dao.update(person) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePersonnel(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}
