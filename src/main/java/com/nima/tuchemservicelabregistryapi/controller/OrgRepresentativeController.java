package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.OrgRepresentativeDAO;
import com.nima.tuchemservicelabregistryapi.dao.OrganizationDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.OrgRepresentative;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/organization-representative")
public class OrgRepresentativeController {
    private final OrgRepresentativeDAO dao;
    private final OrganizationDAO organizationDAO;

    public OrgRepresentativeController(OrgRepresentativeDAO dao, OrganizationDAO organizationDAO) {
        this.dao = dao;
        this.organizationDAO = organizationDAO;
    }

    @GetMapping()
    public ResponseEntity<Data<OrgRepresentative>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<OrgRepresentative> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
            res.records.forEach(orgRep -> {
                orgRep.setOrganizations(organizationDAO.getOrganizationsOfRepresentative(orgRep.getId()));
            });
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgRepresentative> getById(@PathVariable("id") Long id) {
        OrgRepresentative representative;
        try {
            representative = this.dao.getById(id);
            representative.setOrganizations(organizationDAO.getOrganizationsOfRepresentative(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(representative, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createOrgRepresentative(@RequestBody OrgRepresentative representative) {
        int newId;
        if ((newId = dao.create(representative)) > 0) return new ResponseEntity<Integer>(newId, HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateOrgRepresentative(@RequestBody OrgRepresentative representative) {
        List<Organization> orgsOfRepInDB = organizationDAO.getOrganizationsOfRepresentative(representative.getId());
        if (dao.update(representative, orgsOfRepInDB) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/remove-representative/{orgId}/{repId}")
    public ResponseEntity removeOrgRepresentative(@PathVariable("orgId") Long orgId, @PathVariable("repId") Long repId) {
        if (dao.deleteForOrganization(repId, orgId) != 1)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/add-representative/{orgId}/{repId}")
    public ResponseEntity addOrgRepresentative(@PathVariable("orgId") Long orgId, @PathVariable("repId") Long repId) {
        if (dao.addForOrganization(repId, orgId) != 0) return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrgRepresentative(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}
