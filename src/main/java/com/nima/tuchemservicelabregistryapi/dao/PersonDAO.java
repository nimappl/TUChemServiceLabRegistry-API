package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO implements DAO<Person> {
    private final JdbcTemplate jdbcTemplate;

    public final RowMapper<Person> rowMapper = (rs, rowNum) -> {
        Person person = new Person();
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
        return person;
    };

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<Person> list(Data<Person> template) {
        template.count = jdbcTemplate.queryForObject(template.countQuery("vPerson", "PersonID"), Integer.class);
        template.records = jdbcTemplate.query(template.selectQuery("vPerson", "PersonID"), rowMapper);
        return template;
    }

    public List<Person> queryByFullName(String fullName) {
        return jdbcTemplate.query("EXECUTE SearchPersonByFullName ?", rowMapper, fullName);
    }

    @Override
    public Person getById(Long id) {
        String sql = "SELECT * FROM Person WHERE PersonID = ?";
        Person person = null;
        try {
            person = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return person;
    }

    @Override
    public int create(Person person) {
        String sql = "EXECUTE CreatePerson ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{person.getNationalNumber(), person.getFirstName(),
                person.getLastName(), person.getPhoneNumber(), person.getEmail(),person.getGender(), person.getTypeStdn(),
                person.getTypeProf(), person.getTypeLab(), person.getTypeOrg()}, Integer.class);
    }

    @Override
    public int update(Person person) {
        return jdbcTemplate.update("UPDATE Person SET PNationalNumber=?, PFirstName=?, PLastName=?, PPhoneNumber=?, PEmail=?, PGender=?, PTypeLab=?, PTypeProf=?, PTypeStdn=?, PTypeOrg=? WHERE PersonID=?",
                person.getNationalNumber(), person.getFirstName(), person.getLastName(), person.getPhoneNumber(), person.getEmail(), person.getGender(), person.getTypeLab(), person.getTypeProf(),person.getTypeStdn(), person.getTypeOrg(), person.getId());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("UPDATE Person SET DDate=GETDATE() WHERE PersonID=?", id);
    }
}
