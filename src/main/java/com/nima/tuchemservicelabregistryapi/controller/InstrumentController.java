package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.InstrumentDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Instrument;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/instrument")
public class InstrumentController {

    private final InstrumentDAO dao;

    public InstrumentController(InstrumentDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<Instrument>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Instrument> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getById(@PathVariable("id") Long id) {
        Instrument instrument;
        try {
            instrument = this.dao.getById(id).get();
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(instrument, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createInstrument(@RequestBody Instrument instrument) {
        if (dao.create(instrument) == 1) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @PutMapping("/update")
    public ResponseEntity updateInstrument(@RequestBody Instrument instrument) {
        if (dao.update(instrument) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInstrument(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity((HttpStatus.NOT_MODIFIED));
    }
}