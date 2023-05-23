package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.OrgRepresentativeDAO;
import com.nima.tuchemservicelabregistryapi.dao.OrganizationDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationDAO dao;
    private final OrgRepresentativeDAO orgRepresentativeDAO;

    public OrganizationController(OrganizationDAO dao, OrgRepresentativeDAO orgRepresentativeDAO) {
        this.dao = dao;
        this.orgRepresentativeDAO = orgRepresentativeDAO;
    }

    @GetMapping()
    public ResponseEntity<Data<Organization>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<Organization> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/query-by-name/{name}")
    public ResponseEntity<List<Organization>> queryByName(@PathVariable("name") String name) {
        List<Organization> result = dao.queryByName(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getById(@PathVariable("id") Long id) {
        Organization organization;
        try {
            organization = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createOrganization(@RequestBody Organization organization) {
        int newId;
        if ((newId = dao.create(organization)) > 0) return new ResponseEntity<Integer>(newId, HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateOrganization(@RequestBody Organization organization) {
        if (dao.update(organization) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}

