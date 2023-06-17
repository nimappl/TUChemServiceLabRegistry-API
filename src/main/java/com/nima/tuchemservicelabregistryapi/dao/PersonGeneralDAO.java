package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Organization;
import com.nima.tuchemservicelabregistryapi.model.PersonGeneral;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonGeneralDAO implements DAO<PersonGeneral> {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    private final LabPersonnelDAO labPersonnelDAO;
    private final TUProfessorDAO professorDAO;
    private final TUStudentDAO studentDAO;
    private final OrgRepresentativeDAO orgRepDAO;
    private final OrganizationDAO organizationDAO;
    private final EduGroupDAO eduGroupDAO;
    private final EduFieldDAO eduFieldDAO;

    private final RowMapper<PersonGeneral> rowMapper = (rs, rowNum) -> {
        PersonGeneral person = new PersonGeneral();
        person.setId(rs.getLong("PersonID"));
        person.setNationalNumber(rs.getString("PNationalNumber"));
        person.setFirstName(rs.getString("PFirstName"));
        person.setLastName(rs.getString("PLastName"));
        person.setPhoneNumber(rs.getString("PPhoneNumber"));
        person.setEmail(rs.getString("PEmail"));
        person.setGender((Boolean) rs.getObject("PGender"));
        person.setTypeStdn(rs.getBoolean("PTypeStdn"));
        person.setTypeProf(rs.getBoolean("PTypeProf"));
        person.setTypeLab(rs.getBoolean("PTypeLab"));
        person.setTypeOrg(rs.getBoolean("PTypeOrg"));
        person.setLabPersonnelCode(rs.getString("LPCode"));
        person.setLabPost(rs.getString("LPPost"));
        person.setProfPersonnelCode(rs.getString("ProfPersonnelCode"));
        person.setProfEduGroupId((Long) rs.getObject("ProfEduGroupID"));
        person.setProfGrantBalance((Long) rs.getObject("ProfGrantBalance"));
        person.setStdnCode(rs.getString("StCode"));
        person.setStdnEduLevel((Short) rs.getObject("StLevel"));
        person.setStdnEduFieldId((Long) rs.getObject("StEduFieldID"));
        return person;
    };

    public PersonGeneralDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO, LabPersonnelDAO labPersonnelDAO, TUProfessorDAO professorDAO, TUStudentDAO studentDAO, OrgRepresentativeDAO orgRepDAO, OrganizationDAO organizationDAO, EduGroupDAO eduGroupDAO, EduFieldDAO eduFieldDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
        this.labPersonnelDAO = labPersonnelDAO;
        this.professorDAO = professorDAO;
        this.studentDAO = studentDAO;
        this.orgRepDAO = orgRepDAO;
        this.organizationDAO = organizationDAO;
        this.eduGroupDAO = eduGroupDAO;
        this.eduFieldDAO = eduFieldDAO;
    }

    @Override
    public Data<PersonGeneral> list(Data<PersonGeneral> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vPersonGeneral", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vPersonGeneral", "PersonID"), rowMapper);
        template.records.forEach(person -> {
            person.setProfEduGroup(eduGroupDAO.getById(person.getProfEduGroupId()));
            person.setStdnEduField(eduFieldDAO.getById(person.getStdnEduFieldId()));
            person.setOrgRepOrganizations(organizationDAO.getOrganizationsOfRepresentative(person.getId()));
        });
        return template;
    }

    @Override
    public PersonGeneral getById(Long id) {
        String sql = "SELECT * FROM vPersonGeneralAll WHERE PersonID=" + id;
        PersonGeneral person;
        try {
            person = jdbcTemplate.queryForObject(sql, rowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        person.setProfEduGroup(eduGroupDAO.getById(person.getProfEduGroupId()));
        person.setStdnEduField(eduFieldDAO.getById(person.getStdnEduFieldId()));
        person.setOrgRepOrganizations(organizationDAO.getOrganizationsOfRepresentative(person.getId()));
        return person;
    }

    @Override
    public int create(PersonGeneral person) {
        person.setId((long) personDAO.create(person.asPerson()));
        if (person.getTypeLab()) {
            labPersonnelDAO.create(person.asLabPersonnel());
        }
        if (person.getTypeProf()) {
            professorDAO.create(person.asProfessor());
        }
        if (person.getTypeStdn()) {
            studentDAO.create(person.asStudent());
        }
        if (person.getTypeOrg()) {
            orgRepDAO.create(person.asOrgRepresentative());
        }
        return (int)(long) person.getId();
    }

    @Override
    public int update(PersonGeneral person) {
        PersonGeneral personInDB = getById(person.getId());
        personDAO.update(person.asPerson());
        if (person.getTypeLab()) {
            if (personInDB.getTypeLab()) {
                labPersonnelDAO.update(person.asLabPersonnel());
            } else {
                labPersonnelDAO.create(person.asLabPersonnel());
            }
        } else if (!person.getTypeLab() && personInDB.getTypeLab()) {
            labPersonnelDAO.delete(person.getId());
        }
        if (person.getTypeProf()) {
            if (personInDB.getTypeProf()) {
                professorDAO.update(person.asProfessor());
            } else {
                professorDAO.create(person.asProfessor());
            }
        } else if (!person.getTypeProf() && personInDB.getTypeProf()) {
            professorDAO.delete(person.getId());
        }
        if (person.getTypeStdn()) {
            if (personInDB.getTypeStdn()) {
                studentDAO.update(person.asStudent());
            } else {
                studentDAO.create(person.asStudent());
            }
        } else if (!person.getTypeStdn() && personInDB.getTypeStdn()) {
            studentDAO.delete(person.getId());
        }
        if (person.getTypeOrg()) {
            if (personInDB.getTypeOrg()) {
                List<Organization> orgsOfRepInDB = organizationDAO.getOrganizationsOfRepresentative(person.getId());
                orgRepDAO.update(person.asOrgRepresentative(), orgsOfRepInDB);
            } else {
                orgRepDAO.create(person.asOrgRepresentative());
            }
        } else if (!person.getTypeOrg() && personInDB.getTypeOrg()) {
            orgRepDAO.delete(person.getId());
        }
        return 1;
    }

    public int delete(Long id, boolean all, boolean typeLab, boolean typeProf, boolean typeStdn, boolean typeOrg) {
        if (all) {
            this.labPersonnelDAO.delete(id);
            this.professorDAO.delete(id);
            this.studentDAO.delete(id);
            this.orgRepDAO.delete(id);
            this.personDAO.delete(id);
        }
        if (typeLab) labPersonnelDAO.delete(id);
        if (typeProf) professorDAO.delete(id);
        if (typeStdn) studentDAO.delete(id);
        if (typeOrg) orgRepDAO.delete(id);
        return 1;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
