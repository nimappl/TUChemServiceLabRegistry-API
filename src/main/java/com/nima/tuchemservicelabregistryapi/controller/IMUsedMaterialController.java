package com.nima.tuchemservicelabregistryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nima.tuchemservicelabregistryapi.dao.IMUsedMaterialDAO;
import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.IMUsedMaterial;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/used-material")
public class IMUsedMaterialController {
    private final IMUsedMaterialDAO dao;

    public IMUsedMaterialController(IMUsedMaterialDAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public ResponseEntity<Data<IMUsedMaterial>> getAll(@RequestParam("queryParams") String queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        Data<IMUsedMaterial> res;
        try {
            res = objectMapper.readValue(queryParams, Data.class);
        } catch(JsonProcessingException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        res = dao.list(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/query-by-name/{name}")
    public ResponseEntity<List<IMUsedMaterial>> queryByName(@PathVariable("name") String name) {
        List<IMUsedMaterial> materials = dao.queryByName(name);
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IMUsedMaterial> getById(@PathVariable("id") Long id) {
        IMUsedMaterial material;
        try {
            material = this.dao.getById(id);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(material, HttpStatus.OK);
    }

    @GetMapping("/for-maintenance/{id}")
    public ResponseEntity<List<IMUsedMaterial>> getForMaintenance(@PathVariable("id") Long id) {
        List<IMUsedMaterial> usedMaterialList = dao.getUsedMaterialsOfMaintenance(id);
        return new ResponseEntity<>(usedMaterialList, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createIMUsedMaterial(@RequestBody IMUsedMaterial material) {
        int newId;
        if ((newId = dao.create(material)) > 0) return new ResponseEntity<Integer>(newId, HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update")
    public ResponseEntity updateIMUsedMaterial(@RequestBody IMUsedMaterial material) {
        if (dao.update(material) == 1)  return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteIMUsedMaterial(@PathVariable("id") Long id) {
        if (dao.delete(id) == 1) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
}