package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.IMUsedMaterial;
import com.nima.tuchemservicelabregistryapi.model.InstrumentMaintenance;
import com.nima.tuchemservicelabregistryapi.model.OrgRepresentative;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InstrumentMaintenanceDAO implements DAO<InstrumentMaintenance> {
    private final JdbcTemplate jdbcTemplate;
    private final IMUsedMaterialDAO usedMaterialDAO;
    private final InstrumentDAO instrumentDAO;
    private final PersonDAO personDAO;
    private final OrgRepresentativeDAO orgRepresentativeDAO;
    private final OrganizationDAO organizationDAO;

    private final RowMapper<InstrumentMaintenance> rowMapper = (rs, rowNum) -> {
        InstrumentMaintenance maintenance = new InstrumentMaintenance();
        maintenance.setId(rs.getLong("IMaintenanceID"));
        maintenance.setTitle(rs.getString("IMTitle"));
        maintenance.setDate(rs.getTimestamp("IMDate"));
        maintenance.setTotalCost((Long) rs.getObject("IMTotalCost"));
        maintenance.setAdditionalCosts((Long) rs.getObject("IMAdditionalCosts"));
        maintenance.setInvoiceNo(rs.getString("IMInvoiceNo"));
        maintenance.setInstrumentId((Long) rs.getObject("InstrumentID"));
        maintenance.setServicemanId((Long) rs.getObject("ServicemanID"));
        maintenance.setServicingCompanyId((Long) rs.getObject("ServicingCompanyID"));
        maintenance.setDescription(rs.getString("IMDescription"));
        return maintenance;
    };

    public InstrumentMaintenanceDAO(JdbcTemplate jdbcTemplate, IMUsedMaterialDAO usedMaterialDAO, InstrumentDAO instrumentDAO, PersonDAO personDAO, OrgRepresentativeDAO orgRepresentativeDAO, OrganizationDAO organizationDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.usedMaterialDAO = usedMaterialDAO;
        this.instrumentDAO = instrumentDAO;
        this.personDAO = personDAO;
        this.orgRepresentativeDAO = orgRepresentativeDAO;
        this.organizationDAO = organizationDAO;
    }

    @Override
    public Data<InstrumentMaintenance> list(Data<InstrumentMaintenance> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vInstrumentMaintenance", "IMaintenanceID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vInstrumentMaintenance", "IMaintenanceID"), rowMapper);
        template.records.forEach((InstrumentMaintenance maintenance) -> {
            maintenance.setInstrument(instrumentDAO.getById(maintenance.getInstrumentId()));
            if (maintenance.getServicemanId() != null)
                maintenance.setServiceman(orgRepresentativeDAO.getById(maintenance.getServicemanId()));
            if (maintenance.getServicingCompanyId() != null)
                maintenance.setServicingCompany(organizationDAO.getById(maintenance.getServicingCompanyId()));
            maintenance.setUsedMaterialList(usedMaterialDAO.getUsedMaterialsOfMaintenance(maintenance.getId()));
        });
        return template;
    }

    @Override
    public InstrumentMaintenance getById(Long id) {
        InstrumentMaintenance maintenance;
        String query = "SELECT * FROM InstrumentMaintenance WHERE IMaintenanceID=" + id;
        maintenance = jdbcTemplate.queryForObject(query, rowMapper);
        maintenance.setInstrument(instrumentDAO.getById(maintenance.getInstrumentId()));
        if (maintenance.getServicemanId() != null)
            maintenance.setServiceman((OrgRepresentative) orgRepresentativeDAO.getById(maintenance.getServicemanId()));
        if (maintenance.getServicingCompanyId() != null)
            maintenance.setServicingCompany(organizationDAO.getById(maintenance.getServicingCompanyId()));
        maintenance.setUsedMaterialList(usedMaterialDAO.getUsedMaterialsOfMaintenance(maintenance.getId()));
        return maintenance;
    }

    @Override
    public int create(InstrumentMaintenance maintenance) {
        long newId;
        if (maintenance.getServiceman() != null) {
            if (maintenance.getServicemanId() == null || !maintenance.getServiceman().getTypeOrg()) {
                maintenance.getServiceman().setTypeOrg(true);
                maintenance.setServicemanId((long) personDAO.create(maintenance.getServiceman()));
            }
        }
        if (maintenance.getServicingCompany() != null) {
            if (maintenance.getServicingCompanyId() == null) {
                maintenance.setServicingCompanyId((long) organizationDAO.create(maintenance.getServicingCompany()));
            }
        }
        if (maintenance.getServicingCompany() != null && maintenance.getServiceman() != null) {
            if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE OrganizationID=? AND RepresentativeID=?", new Object[]{maintenance.getServicingCompanyId(), maintenance.getServicemanId()}, Integer.class) == 0) {
                jdbcTemplate.update("INSERT INTO Organization_Representative (OrganizationID, RepresentativeID) VALUES (?, ?)", maintenance.getServicingCompanyId(), maintenance.getServicemanId());
            }
        }

        newId = jdbcTemplate.queryForObject("EXECUTE CreateIMaintenance ?, ?, ?, ?, ?, ?, ?, ?, ?",
                new Object[]{maintenance.getTitle(), maintenance.getDate(), maintenance.getTotalCost(), maintenance.getAdditionalCosts(), maintenance.getInvoiceNo(), maintenance.getInstrumentId(), maintenance.getServicemanId(), maintenance.getServicingCompanyId(), maintenance.getDescription()}, Long.class);

        maintenance.getUsedMaterialList().forEach((IMUsedMaterial usedMaterial) -> {
            if (usedMaterial.getId() == null)
                usedMaterial.setId((long) usedMaterialDAO.create(usedMaterial));
            jdbcTemplate.update("INSERT INTO IMaintenance_IMUsedMaterial (MaintenanceID, UsedMaterialID, UsedMaterialQuantity) VALUES (?, ?, ?)",
                    newId, usedMaterial.getId(), usedMaterial.getQuantity());
        });

        return (int) newId;
    }

    @Override
    public int update(InstrumentMaintenance maintenance) {
        InstrumentMaintenance maintenanceInDB = getById(maintenance.getId());
        if (maintenance.getServiceman() != null) {
            if (maintenance.getServicemanId() == null || !maintenance.getServiceman().getTypeOrg()) {
                maintenance.getServiceman().setTypeOrg(true);
                maintenance.setServicemanId((long) personDAO.create(maintenance.getServiceman()));
            }
        }
        if (maintenance.getServicingCompany() != null) {
            if (maintenance.getServicingCompanyId() == null) {
                maintenance.setServicingCompanyId((long) organizationDAO.create(maintenance.getServicingCompany()));
            }
        }

        if (maintenance.getServicingCompanyId() != null && maintenance.getServicemanId() != null) {
            orgRepresentativeDAO.addRepresentativeForOrganization(maintenance.getServicemanId(), maintenance.getServicingCompanyId());
        }

        maintenanceInDB.getUsedMaterialList().forEach(materialInDB -> {
            boolean isRemoved = true;
            for (IMUsedMaterial material : maintenance.getUsedMaterialList()) {
                if (materialInDB.isEqualTo(material)) {
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) usedMaterialDAO.removeMaterialFromMaintenance(maintenance.getId(), materialInDB.getId());
        });
        maintenance.getUsedMaterialList().forEach(material -> {
            boolean isNew = true;
            for (IMUsedMaterial materialInDB : maintenanceInDB.getUsedMaterialList()) {
                if (materialInDB.isEqualTo(material)) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                if (material.getId() == null) material.setId((long) usedMaterialDAO.create(material));
                usedMaterialDAO.addMaterialToMaintenance(maintenance.getId(), material.getId(), material.getQuantity());
            }
        });

        return jdbcTemplate.update("UPDATE InstrumentMaintenance SET IMTitle=?, IMDate=?, IMTotalCost=?, IMAdditionalCosts=?, IMInvoiceNo=?, ServicemanID=?, ServicingCompanyID=?, IMDescription=? WHERE IMaintenanceID=?",
                maintenance.getTitle(), maintenance.getDate(), maintenance.getTotalCost(), maintenance.getAdditionalCosts(), maintenance.getInvoiceNo(), maintenance.getServicemanId(), maintenance.getServicingCompanyId(), maintenance.getDescription(), maintenance.getId());
    }

    @Override
    public int delete(Long id) {
        jdbcTemplate.update("DELETE FROM IMaintenance_IMUsedMaterial WHERE MaintenanceID=" + id);
        return jdbcTemplate.update("DELETE FROM InstrumentMaintenance WHERE IMaintenanceID=" + id);
    }
}
