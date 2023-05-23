package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.IMUsedMaterial;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IMUsedMaterialDAO implements DAO<IMUsedMaterial> {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<IMUsedMaterial> rowMapper = (rs, rowNum) -> {
        IMUsedMaterial material = new IMUsedMaterial();
        material.setId(rs.getLong("UsedMaterialID"));
        material.setName(rs.getString("UMName"));
        material.setManufacturer(rs.getString("UMManufacturer"));
        material.setPrice(rs.getLong("UMPrice"));
        material.setType(rs.getShort("UMType"));
        return material;
    };

    private final RowMapper<IMUsedMaterial> withQtyRowMapper = (rs, rowNum) -> {
        IMUsedMaterial material = new IMUsedMaterial();
        material.setId(rs.getLong("UsedMaterialID"));
        material.setName(rs.getString("UMName"));
        material.setManufacturer(rs.getString("UMManufacturer"));
        material.setPrice(rs.getLong("UMPrice"));
        material.setType(rs.getShort("UMType"));
        material.setQuantity(rs.getDouble("UsedMaterialQuantity"));
        material.setMaintenanceId(rs.getLong("MaintenanceID"));
        return material;
    };

    public IMUsedMaterialDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<IMUsedMaterial> list(Data<IMUsedMaterial> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vIMUsedMaterial", "UsedMaterialID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vIMUsedMaterial", "UsedMaterialID"), rowMapper);
        return template;
    }

    public List<IMUsedMaterial> queryByName(String name) {
        String query = "SELECT * FROM vIMUsedMaterial WHERE UMName LIKE '%" + name + "%'";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<IMUsedMaterial> getUsedMaterialsOfMaintenance(Long maintenanceId) {
        String query = "SELECT * FROM vUsedMaterialsOfMaintenances WHERE MaintenanceID=" + maintenanceId;
        return jdbcTemplate.query(query, withQtyRowMapper);
    }

    @Override
    public IMUsedMaterial getById(Long id) {
        String sql = "SELECT * FROM IMUsedMaterial WHERE UsedMaterialID=?";
        IMUsedMaterial material = null;
        try {
            material = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return material;
    }

    @Override
    public int create(IMUsedMaterial material) {
        return jdbcTemplate.queryForObject("EXECUTE CreateIMUsedMaterial ?, ?, ?, ?",
                new Object[]{material.getName(), material.getManufacturer(), material.getPrice(), material.getType()}, Integer.class);
    }

    @Override
    public int update(IMUsedMaterial material) {
        return jdbcTemplate.update("EXECUTE UpdateIMUsedMaterial ?, ?, ?, ?, ?",
                material.getId(), material.getName(), material.getManufacturer(), material.getPrice(), material.getType());
    }

    public void addMaterialToMaintenance(Long maintenanceId, Long materialId, Double quantity) {
        jdbcTemplate.update("INSERT INTO IMaintenance_IMUsedMaterial (MaintenanceID, UsedMaterialID, UsedMaterialQuantity) VALUES (?, ?, ?)",
                maintenanceId, materialId, quantity);
    }

    public void removeMaterialFromMaintenance(Long maintenanceId, Long materialId) {
        jdbcTemplate.update("DELETE FROM IMaintenance_IMUsedMaterial WHERE MaintenanceID=? AND UsedMaterialID=?", maintenanceId, materialId);
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE IMUsedMaterial SET DDate=GETDATE() WHERE UsedMaterialID=?", id);
    }
}
