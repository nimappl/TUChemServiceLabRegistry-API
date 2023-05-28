package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.OrgRepresentative;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrgRepresentativeDAO implements DAO<OrgRepresentative> {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OrgRepresentative> rowMapper = (rs, rowNum) -> {
        OrgRepresentative orgRepresentative = new OrgRepresentative();
        orgRepresentative.setId(rs.getLong("PersonID"));
        orgRepresentative.setNationalNumber(rs.getString("PNationalNumber"));
        orgRepresentative.setFirstName(rs.getString("PFirstName"));
        orgRepresentative.setLastName(rs.getString("PLastName"));
        orgRepresentative.setPhoneNumber(rs.getString("PPhoneNumber"));
        orgRepresentative.setEmail(rs.getString("PEmail"));
        orgRepresentative.setGender((Boolean) rs.getObject("PGender"));
        orgRepresentative.setTypeStdn(rs.getBoolean("PTypeStdn"));
        orgRepresentative.setTypeProf(rs.getBoolean("PTypeProf"));
        orgRepresentative.setTypeLab(rs.getBoolean("PTypeLab"));
        orgRepresentative.setTypeOrg(rs.getBoolean("PTypeOrg"));
        return orgRepresentative;
    };

    public OrgRepresentativeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<OrgRepresentative> list(Data<OrgRepresentative> template) {
        template.filters.add(new Filter("PTypeOrg", "1"));
        template.count = jdbcTemplate.queryForObject(template.countQuery("vPerson", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vPerson", "PersonID"), rowMapper);
        template.filters = new ArrayList<>();
        return template;
    }

    public List<OrgRepresentative> getRepresentativesOfOrganization(Long orgId) {
        return jdbcTemplate.query("SELECT * FROM vRepresentativesOfOrganizations WHERE OrganizationID=" + orgId, rowMapper);
    }

    @Override
    public OrgRepresentative getById(Long id) {
        OrgRepresentative orgRep;
        try {
            orgRep = jdbcTemplate.queryForObject("SELECT * FROM Person WHERE PersonID=? AND PTypeOrg=1", new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            orgRep = null;
        }
        return orgRep;
    }

    @Override
    public int create(OrgRepresentative orgRep) {
        if (orgRep.getOrganizations() != null) {
            orgRep.getOrganizations().forEach(org -> {
                addRepresentativeForOrganization(orgRep.getId(), org.getId());
            });
        }
        return 1;
    }

    @Override
    public int update(OrgRepresentative orgRep) {
        return 0;
    }

    public int update(OrgRepresentative orgRep, List<Organization> orgsOfRepInDB) {
        System.out.println(orgRep.getId());
        orgsOfRepInDB.forEach(orgInDB -> {
            boolean isRemoved = true;
            for (Organization organization : orgRep.getOrganizations()) {
                if (orgInDB.getId().equals(organization.getId())) {
                    isRemoved = false;
                    break;
                }
            }
            if (isRemoved) removeOrganizationForRepresentative(orgInDB.getId(), orgRep.getId());
        });
        orgRep.getOrganizations().forEach(organization -> {
            boolean isNew = true;
            for (Organization orgInDB: orgsOfRepInDB) {
                if (organization.getId().equals(orgInDB.getId())) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) addRepresentativeForOrganization(orgRep.getId(), organization.getId());
        });
        return 1;
    }

    public int addRepresentativeForOrganization(Long repId, Long orgId) {
        jdbcTemplate.update("UPDATE Person SET PTypeOrg=1 WHERE PersonID=" + repId);
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE OrganizationID=? AND RepresentativeID=?", new Object[]{orgId, repId}, Integer.class) == 0)
            jdbcTemplate.update("INSERT INTO Organization_Representative (OrganizationID, RepresentativeID) VALUES (?, ?)", orgId, repId);
        return 1;
    }

    public int removeRepresentativeForOrganization(Long repId, Long orgId) {
        jdbcTemplate.update("DELETE FROM Organization_Representative WHERE RepresentativeID=? AND OrganizationID=?", repId, orgId);
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE RepresentativeID=" + repId, Integer.class) == 0) {
            if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM InstrumentMaintenance WHERE ServicemanID=?", new Object[]{repId}, Integer.class) == 0) {
                jdbcTemplate.update("UPDATE Person SET PTypeOrg=0 WHERE PersonID=?", repId);
            }
        }
        return 1;
    }

    public int removeOrganizationForRepresentative(Long orgId, Long repId) {
        return jdbcTemplate.update("DELETE FROM Organization_Representative WHERE OrganizationID=? AND RepresentativeID=?", orgId, repId);
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("EXECUTE DeleteOrgRepresentative ?", id);
    }
}
