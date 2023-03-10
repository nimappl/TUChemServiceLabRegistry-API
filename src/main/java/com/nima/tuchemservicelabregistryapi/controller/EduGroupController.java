package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.EduGroupDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.EduGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/edu-group")
public class EduGroupController {
    private final EduGroupDAO dao;

    public EduGroupController(EduGroupDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<EduGroup>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<EduGroup> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EduGroup> getById(@PathVariable("id") Long id) {
        EduGroup EduGroup;
        try {
            EduGroup = this.dao.getById(id).get();
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(EduGroup, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createEduGroup(@RequestBody EduGroup EduGroup) {
        if (dao.create(EduGroup) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updateEduGroup(@RequestBody EduGroup EduGroup) {
        if (dao.update(EduGroup) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEduGroup(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}
