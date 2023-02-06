package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.Instrument;
import com.nima.tuchemservicelabregistryapi.model.Person;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonDAO implements DAO<Person> {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Person> rowMapper = (rs, rowNum) -> {
        Person person = new Person();
        person.setId(rs.getInt("PersonID"));
        person.setNationalNumber(rs.getString("PNationalNumber"));
        person.setFirstName(rs.getString("PFirstName"));
        person.setLastName(rs.getString("PLastName"));
        person.setPhoneNumber(rs.getString("PPhoneNumber"));
        person.setEmail(rs.getString("PEmail"));
        person.setGender(rs.getByte("PGender"));
        person.setCustomerId(rs.getInt("CustomerID"));
        person.setType(rs.getShort("PType"));
        person.setUsername(rs.getString("PUsername"));
        person.setPassword(rs.getString("PPassword"));
        return person;
    };

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Data<Person> list(Data<Person> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM personv";
        String selectQuery = "SELECT * FROM personv";

        // WHERE clause for each of filters
        for (Filter filter : template.filters) {
            if (firstFilter) { queryBody += " WHERE "; firstFilter = false; }
            else queryBody += " AND ";
            queryBody += filter.key + " LIKE \"%" + filter.value + "%\" ";
        }
        countQuery += queryBody;

        // ORDER BY
        if (template.sortBy != null) {
            String sortType = template.sortType == 0 ? "ASC " : "DESC ";
            queryBody += " ORDER BY " + template.sortBy + " " + sortType;
        }

        // PAGINATION
        if (template.pageSize != 0) {
            queryBody += " LIMIT " + template.pageSize + " OFFSET " + ((template.pageNumber - 1) * template.pageSize);
        }
        selectQuery += queryBody;

        template.count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        template.records = jdbcTemplate.query(selectQuery, rowMapper);
        return template;
    }

    @Override
    public Optional<Person> getById(Long id) {
        String sql = "SELECT * FROM Person WHERE PersonID = ?";
        Person person = null;
        try {
            person = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(person);
    }

    @Override
    public int create(Person person) {
        String sql = "INSERT INTO Person(PNationalNumber, PFirstName, PLastName, PPhoneNumber, PEmail, PGender, CustomerID, PUsername, PPassword)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, person.getNationalNumber(), person.getFirstName(), person.getLastName(),
                person.getPhoneNumber(), person.getEmail(),person.getGender(), person.getCustomerId() == 0 ? null : person.getCustomerId(),
                person.getUsername(), person.getPassword());
    }

    @Override
    public int update(Person person) {
        return jdbcTemplate.update("CALL UpdatePerson(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", person.getId(), person.getNationalNumber(), person.getFirstName(), person.getLastName(),
                person.getPhoneNumber(), person.getEmail(), person.getGender(),  person.getCustomerId() == 0 ? null : person.getCustomerId(), person.getUsername(), person.getPassword());
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("CALL DeletePerson(?)", id);
    }
}
