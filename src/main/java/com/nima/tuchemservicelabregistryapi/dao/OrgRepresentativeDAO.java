package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.OrgRepresentative;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrgRepresentativeDAO implements DAO<OrgRepresentative> {
    private JdbcTemplate jdbcTemplate;
    private PersonDAO personDAO;
    private RowMapper<OrgRepresentative> rowMapper = (rs, rowNum) -> {
        OrgRepresentative orgRepresentative = new OrgRepresentative();
        orgRepresentative.setId(rs.getLong("PersonID"));
        orgRepresentative.setNationalNumber(rs.getString("PNationalNumber"));
        orgRepresentative.setFirstName(rs.getString("PFirstName"));
        orgRepresentative.setLastName(rs.getString("PLastName"));
        orgRepresentative.setPhoneNumber(rs.getString("PPhoneNumber"));
        orgRepresentative.setEmail(rs.getString("PEmail"));
        orgRepresentative.setGender((Boolean) rs.getObject("PGender"));
        orgRepresentative.setCustomerId((Long) rs.getObject("CustomerID"));
        orgRepresentative.setTypeStdn(rs.getBoolean("PTypeStdn"));
        orgRepresentative.setTypeProf(rs.getBoolean("PTypeProf"));
        orgRepresentative.setTypeLab(rs.getBoolean("PTypeLab"));
        orgRepresentative.setTypeOrg(rs.getBoolean("PTypeOrg"));
        orgRepresentative.setUsername(rs.getString("PUsername"));
        orgRepresentative.setPassword(rs.getString("PPassword"));
        return orgRepresentative;
    };

    public OrgRepresentativeDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    @Override
    public Data<OrgRepresentative> list(Data<OrgRepresentative> template) {
        template.filters.add(new Filter("PTypeOrg", "1"));
        template.count = jdbcTemplate.queryForObject(template.countQuery("vPerson", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vPerson", "PersonID"), rowMapper);
        return template;
    }

    public List<OrgRepresentative> getRepresentativesOfOrganization(Long orgId) {
        return jdbcTemplate.query("SELECT * FROM vRepresentativesOfOrganizations WHERE OrganizationID="+orgId, rowMapper);
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
        int id;
        if (orgRep.getId() == null) {
            id = personDAO.create(orgRep.asPerson());
        } else {
            id = (int)((long) orgRep.getId());
        }

        jdbcTemplate.update("UPDATE Person SET PTypeOrg=1 WHERE PersonID=?", id);

        if (orgRep.getOrganizations() != null) {
            orgRep.getOrganizations().forEach(org -> {
                jdbcTemplate.update("INSERT INTO Organization_Representative (OrganizationID, RepresentativeID) VALUES (?, ?)",
                        org.getId(), id);
            });
        }
        return id;
    }

    @Override
    public int update(OrgRepresentative orgRep) {
        return 0;
    }

    public int update(OrgRepresentative orgRep, List<Organization> orgsOfRepInDB) {

        personDAO.update(orgRep.asPerson());
        orgsOfRepInDB.forEach(orgInDB -> {
            if (!orgRep.getOrganizations().contains(orgInDB))
                jdbcTemplate.update("DELETE FROM Organization_Representative WHERE OrganizationID=? AND RepresentativeID=?",
                        orgInDB.getId(), orgRep.getId());
        });
        orgRep.getOrganizations().forEach(org -> {
            if (!orgsOfRepInDB.contains(org)) {
                jdbcTemplate.update("INSERT INTO Organization_Representative (OrganizationID, RepresentativeID) VALUES (?, ?)",
                        org.getId(), orgRep.getId());
            }
        });
        return 1;
    }

    public int addForOrganization(Long repId, Long orgId) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE OrganizationID=? AND RepresentativeID=?", new Object[]{orgId, repId}, Integer.class) == 0) {
            jdbcTemplate.update("INSERT INTO Organization_Representative (OrganizationID, RepresentativeID) VALUES (?, ?)", orgId, repId);
        }
        return 1;
    }

    public int deleteForOrganization(Long repId, Long orgId) {
        OrgRepresentative rep = getById(repId);
        jdbcTemplate.update("DELETE FROM Organization_Representative WHERE RepresentativeID=? AND OrganizationID=?", repId, orgId);
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE RepresentativeID=" + repId, Integer.class) == 0) {
            if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM InstrumentMaintenance WHERE ServicemanID=?", new Object[]{repId}, Integer.class) == 0) {
                jdbcTemplate.update("UPDATE Person SET PTypeOrg=0 WHERE PersonID=?", repId);
            }
            if (rep.getTypeStdn().equals(false) && rep.getTypeLab().equals(false) && rep.getTypeProf().equals(false) && rep.getCustomerId() == null) {
                personDAO.delete(repId);
            }
        }
        return 1;
    }

    @Override
    public int delete(Long id) {
        OrgRepresentative rep = getById(id);
        jdbcTemplate.update("DELETE FROM Organization_Representative WHERE RepresentativeID=?", id);
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Organization_Representative WHERE RepresentativeID=" + id, Integer.class) == 0) {
            if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM InstrumentMaintenance WHERE ServicemanID=?", new Object[]{id}, Integer.class) == 0) {
                jdbcTemplate.update("UPDATE Person SET PTypeOrg=0 WHERE PersonID=?", id);
            }
            if (rep.getTypeStdn().equals(false) && rep.getTypeLab().equals(false) && rep.getTypeProf().equals(false) && rep.getCustomerId() == null) {
                personDAO.delete(id);
            }
        }
        return 1;
    }
}
