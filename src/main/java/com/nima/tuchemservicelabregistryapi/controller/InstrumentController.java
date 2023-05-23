package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.InstrumentDAO;
import com.nima.tuchemservicelabregistryapi.dao.InstrumentOperatorDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Instrument;
import com.nima.tuchemservicelabregistryapi.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/instrument")
public class InstrumentController {

    private final InstrumentDAO dao;
    private final InstrumentOperatorDAO operatorDAO;

    public InstrumentController(InstrumentDAO dao, InstrumentOperatorDAO operatorDAO) {
        this.dao = dao;
        this.operatorDAO = operatorDAO;
    }

    @GetMapping()
    public ResponseEntity<Data<Instrument>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Instrument> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getById(@PathVariable("id") Long id) {
        Instrument instrument;
        try {
            instrument = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(instrument, HttpStatus.OK);
    }

    @GetMapping("/get-operator-candidates/{name}")
    public ResponseEntity<List<Person>> getCandidates(@PathVariable("name") String name) {
        return new ResponseEntity(operatorDAO.queryOperatorCandidates(name), HttpStatus.OK);
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