package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.InstrumentMaintenanceDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.InstrumentMaintenance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/instrument-maintenance")
public class InstrumentMaintenanceController {
    private final InstrumentMaintenanceDAO dao;

    public InstrumentMaintenanceController(InstrumentMaintenanceDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<InstrumentMaintenance>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<InstrumentMaintenance> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrumentMaintenance> getById(@PathVariable("id") Long id) {
        InstrumentMaintenance maintenance;
        try {
            maintenance = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(maintenance, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createInstrumentMaintenance(@RequestBody InstrumentMaintenance maintenance) {
        if (dao.create(maintenance) > 0) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updateInstrumentMaintenance(@RequestBody InstrumentMaintenance maintenance) {
        if (dao.update(maintenance) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInstrumentMaintenance(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}
