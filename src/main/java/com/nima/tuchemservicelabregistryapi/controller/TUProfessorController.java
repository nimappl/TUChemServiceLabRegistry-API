package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.TUProfessorDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.TUProfessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/professor")
public class TUProfessorController {
    private final TUProfessorDAO dao;

    public TUProfessorController(TUProfessorDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<TUProfessor>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<TUProfessor> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/grant")
    public ResponseEntity<Data<TUProfessor>> getGrantList(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<TUProfessor> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.grantList(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TUProfessor> getById(@PathVariable("id") Long id) {
        TUProfessor person = dao.getById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createPersonnel(@RequestBody TUProfessor person) {
        if (dao.create(person) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updatePersonnel(@RequestBody TUProfessor person) {
        if (dao.update(person) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePersonnel(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}
