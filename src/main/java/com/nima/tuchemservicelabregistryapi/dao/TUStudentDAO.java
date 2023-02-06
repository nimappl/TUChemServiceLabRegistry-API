package com.nima.tuchemservicelabregistryapi.dao;

import com.nima.tuchemservicelabregistryapi.model.Data;
import com.nima.tuchemservicelabregistryapi.model.Filter;
import com.nima.tuchemservicelabregistryapi.model.TUStudent;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TUStudentDAO implements DAO<TUStudent> {
    private JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    private final EduFieldDAO eduFieldDAO;

    private RowMapper<TUStudent> rowMapper = (rs, rowNum) -> {
        TUStudent student = new TUStudent();
        student.setId(rs.getInt("PersonID"));
        student.setNationalNumber(rs.getString("PNationalNumber"));
        student.setFirstName(rs.getString("PFirstName"));
        student.setLastName(rs.getString("PLastName"));
        student.setPhoneNumber(rs.getString("PPhoneNumber"));
        student.setEmail(rs.getString("PEmail"));
        student.setGender(rs.getByte("PGender"));
        student.setCustomerId(rs.getInt("CustomerID"));
        student.setType(rs.getShort("PType"));
        student.setUsername(rs.getString("PUsername"));
        student.setPassword(rs.getString("PPassword"));
        student.setStCode(rs.getString("StCode"));
        student.setEduFieldId(rs.getLong("StEduFieldID"));
        return student;
    };

    public TUStudentDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO, EduFieldDAO eduFieldDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
        this.eduFieldDAO = eduFieldDAO;
    }

    @Override
    public Data<TUStudent> list(Data<TUStudent> template) {
        boolean firstFilter = true;
        String queryBody = "";
        String countQuery = "SELECT COUNT(*) FROM tustudentv";
        String selectQuery = "SELECT * FROM tustudentv";

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

        template.records.forEach((TUStudent student) -> {
            if (student.getEduFieldId() != 0) {
                student.setEduField(eduFieldDAO.getById(student.getEduFieldId()).get());
            }
        });

        return template;
    }

    @Override
    public Optional<TUStudent> getById(Long id) {
        String sql = "SELECT * FROM tustudentv WHERE PersonID = ?";
        TUStudent student = null;
        try {
            student = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            if (student.getEduFieldId() != 0) {
                student.setEduField(eduFieldDAO.getById(student.getEduFieldId()).get());
            }
        } catch (DataAccessException ex) {
            System.out.println("Item not found: " + id);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public int create(TUStudent student) {
        if (student.getId() != 0) {
            if (student.getUsername() == null || student.getUsername() == "") {
                student.setUsername(student.getNationalNumber());
                student.setPassword(student.getNationalNumber());
            }
            jdbcTemplate.update("UPDATE Person SET PType=?, PUsername=?, PPassword=? WHERE PersonID=?",
                    2, student.getNationalNumber(), student.getNationalNumber(), student.getId());

            return jdbcTemplate.update("INSERT INTO TUStudent(PersonID, StCode, StEduFieldID) VALUES(?,?,?)",
                    student.getId(), student.getStCode(), student.getEduFieldId());
        }

        return jdbcTemplate.update("CALL CreateTUStudent(?,?,?,?,?,?,?,?,?,?,?)", student.getNationalNumber(), student.getFirstName(), student.getLastName(),
                student.getPhoneNumber(), student.getEmail(), student.getGender(), student.getCustomerId() == 0 ? null : student.getCustomerId(),
                student.getNationalNumber(), student.getNationalNumber(), student.getStCode(), student.getEduFieldId());
    }

    @Override
    public int update(TUStudent student) {
        return jdbcTemplate.update("CALL UpdateTUStudent(?,?,?,?,?,?,?,?,?,?,?,?)", student.getId(), student.getNationalNumber(), student.getFirstName(),
                student.getLastName(), student.getPhoneNumber(), student.getEmail(),student.getGender(), student.getCustomerId() == 0 ? null : student.getCustomerId(),
                student.getUsername(), student.getPassword(), student.getStCode(), student.getEduFieldId());
    }

    @Override
    public int delete(Long id) {
//        jdbcTemplate.update("UPDATE Person SET PType=0 WHERE PersonID=?", id);
//        return jdbcTemplate.update("UPDATE TUStudent SET DDate=CURRENT_TIMESTAMP() WHERE PersonID=?", id);
        return jdbcTemplate.update("CALL DeleteTUStudent(?)", id);
    }
}
