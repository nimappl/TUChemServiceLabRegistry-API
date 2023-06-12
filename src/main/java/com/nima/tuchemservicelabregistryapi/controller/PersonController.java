package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.PersonDAO;
import com.nima.tuchemservicelabregistryapi.dao.PersonGeneralDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Person;
import com.nima.tuchemservicelabregistryapi.model.PersonGeneral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonDAO dao;
    private final PersonGeneralDAO generalDAO;

    public PersonController(PersonDAO dao, PersonGeneralDAO generalDAO) {
        this.dao = dao;
        this.generalDAO = generalDAO;
    }

    @GetMapping()
    public ResponseEntity<Data<PersonGeneral>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<PersonGeneral> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = generalDAO.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/query-by-full-name/{fullName}")
    public ResponseEntity<List<Person>> queryByFullName(@PathVariable("fullName") String name) {
        List<Person> result = dao.queryByFullName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") Long id) {
        Person person;
        try {
            person = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/general/{id}")
    public ResponseEntity<PersonGeneral> getGeneralById(@PathVariable("id") Long id) {
        PersonGeneral person;
        try {
            person = this.generalDAO.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createPerson(@RequestBody PersonGeneral person) {
        int newId;
        if ((newId = generalDAO.create(person)) > 0) return new ResponseEntity<Integer>(newId, HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updatePerson(@RequestBody PersonGeneral person) {
        if (generalDAO.update(person) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id,
                                       @RequestParam("All") boolean all,
                                       @RequestParam("TypeLab") boolean typeLab,
                                       @RequestParam("TypeProf") boolean typeProf,
                                       @RequestParam("TypeStdn") boolean typeStdn,
                                       @RequestParam("TypeOrg") boolean typeOrg) {
        if (generalDAO.delete(id, all, typeLab, typeProf, typeStdn, typeOrg) == 1)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
