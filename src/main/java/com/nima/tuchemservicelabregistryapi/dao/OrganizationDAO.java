package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.OrgPhoneNumber;
import com.nima.tuchemservicelabregistryapi.model.OrgRepresentative;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationDAO implements DAO<Organization> {
    private JdbcTemplate jdbcTemplate;
    private final OrgRepresentativeDAO orgRepresentativeDAO;
    private RowMapper<Organization> rowMapper = (rs, rowNum) -> {
        Organization organization = new Organization();
        organization.setId(rs.getLong("OrganizationID"));
        organization.setName(rs.getString("OrgName"));
        organization.setNationalId(rs.getString("OrgNationalID"));
        organization.setRegistrationNo(rs.getString("OrgRegistrationNo"));
        organization.setContractNo(rs.getString("OrgContractNo"));
        organization.setCustomerId((Long) rs.getObject("CustomerID"));
        return organization;
    };

    private RowMapper<OrgPhoneNumber> phoneNumberRowMapper = (rs, rowNum) -> {
        OrgPhoneNumber phoneNumber = new OrgPhoneNumber();
        phoneNumber.setNumber(rs.getString("OrgPhoneNumber"));
        phoneNumber.setSection(rs.getString("OrgPNSection"));
        return phoneNumber;
    };

    public OrganizationDAO(JdbcTemplate jdbcTemplate, OrgRepresentativeDAO orgRepresentativeDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.orgRepresentativeDAO = orgRepresentativeDAO;
    }

    @Override
    public Data<Organization> list(Data<Organization> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vOrganization", "OrganizationID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vOrganization", "OrganizationID"), rowMapper);
        template.records.forEach(org -> {
            org.setPhoneNumbers(jdbcTemplate.query("SELECT * FROM OrganizationPhoneNumber WHERE OrganizationID=" + org.getId(), phoneNumberRowMapper));
        });
        template.records.forEach(org -> {
            org.setRepresentatives(orgRepresentativeDAO.getRepresentativesOfOrganization(org.getId()));
        });
        return template;
    }

    public List<Organization> getOrganizationsOfRepresentative(Long orgRepId) {
        return jdbcTemplate.query("SELECT * FROM OrganizationsOfRepresentatives WHERE RepresentativeID=?" + orgRepId, rowMapper);
    }

    public List<Organization> queryByName(String name) {
        String query = "SELECT * FROM vOrganization WHERE OrgName LIKE '%" + name + "%'";
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Organization getById(Long id) {
        String query = "SELECT * FROM Organization WHERE OrganizationID=?";
        Organization org = null;
        try {
            org = jdbcTemplate.queryForObject(query, new Object[]{id}, rowMapper);
            org.setPhoneNumbers(jdbcTemplate.query("SELECT * FROM OrganizationPhoneNumber WHERE OrganizationID=" + org.getId(), phoneNumberRowMapper));
            org.setRepresentatives(orgRepresentativeDAO.getRepresentativesOfOrganization(id));
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return org;
    }

    @Override
    public int create(Organization org) {
        long id;
        String createQuery = "EXEC CreateOrganization @name=?, @nationalId=?, @regNumber=?, @contractNo=?, @custId=?";
        id = jdbcTemplate.queryForObject(createQuery, new Object[]{org.getName(), org.getNationalId(), org.getRegistrationNo(), org.getContractNo(), org.getCustomerId()}, Long.class);

        if (org.getPhoneNumbers() != null) {
            org.getPhoneNumbers().forEach(phoneNumber -> {
                jdbcTemplate.update("INSERT INTO OrganizationPhoneNumber (OrganizationID, OrgPhoneNumber, OrgPNSection) VALUES (?, ?, ?)",
                        id, phoneNumber.getNumber(), phoneNumber.getSection());
            });
        }

        if (org.getRepresentatives() != null) {
            org.getRepresentatives().forEach(orgRep -> {
                int repId = orgRepresentativeDAO.create(orgRep);
                orgRepresentativeDAO.addForOrganization((long) repId, id);
            });
        }

        return (int)id;
    }

    @Override
    public int update(Organization org) {
        Organization orgInDB = getById(org.getId());

        orgInDB.getPhoneNumbers().forEach((OrgPhoneNumber phoneNumInDB) -> {
            jdbcTemplate.update("DELETE FROM OrganizationPhoneNumber WHERE OrganizationID=? AND OrgPhoneNumber=?", org.getId(), phoneNumInDB.getNumber());
        });

        org.getPhoneNumbers().forEach((OrgPhoneNumber phoneNum) -> {
            jdbcTemplate.update("INSERT INTO OrganizationPhoneNumber (OrganizationID, OrgPhoneNumber, OrgPNSection) VALUES (?, ?, ?)", org.getId(), phoneNum.getNumber(), phoneNum.getSection());
        });

        orgInDB.getRepresentatives().forEach(repInDB -> {
            boolean isRemoved = true;
            for (OrgRepresentative rep : org.getRepresentatives()) {
                if (rep.getId() == null || rep.getId().equals(repInDB.getId())) {
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) orgRepresentativeDAO.deleteForOrganization(repInDB.getId(), org.getId());
        });
        org.getRepresentatives().forEach(rep -> {
            Long repId;
            boolean isNew = true;
            if (rep.getId() == null) repId = (long)orgRepresentativeDAO.create(rep);
            else repId = rep.getId();
            for (OrgRepresentative repInDB : orgInDB.getRepresentatives()) {
                if (repInDB.getId().equals(repId)) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) orgRepresentativeDAO.addForOrganization(repId, org.getId());
        });

        jdbcTemplate.update("UPDATE Organization SET OrgName=?, OrgNationalID=?, OrgRegistrationNo=?, OrgContractNo=?, CustomerID=? WHERE OrganizationID=?",
                org.getName(), org.getNationalId(), org.getRegistrationNo(), org.getContractNo(), org.getCustomerId(), org.getId());
        return 1;
    }

    @Override
    public int delete(Long id) {
        jdbcTemplate.update("DELETE FROM OrganizationPhoneNumber WHERE OrganizationID=?", id);
        getById(id).getRepresentatives().forEach(rep -> {
            orgRepresentativeDAO.deleteForOrganization(rep.getId(), id);
        });
        return jdbcTemplate.update("UPDATE Organization SET DDate=GETDATE() WHERE OrganizationID=?", id);
    }
}
