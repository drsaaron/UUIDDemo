/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.uuiddemo;

import jakarta.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
@Order(3)
public class DumpRawDataCommandLineRunner implements CommandLineRunner {

    @Data
    private static class PersonDataView {

        private String id;

        private String firstName;
        private String lastName;
        private int age;
    }

    @Data
    private static class SchoolDataView {

        private String id;

        private String name;
        private String state;
        private String type;
    }

    @Autowired
    private DataSource dataSource;
    
    public UUID getUuidFromByteArray(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("Byte array must be 16 bytes long");
        }

        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong(); // Get the most significant 64 bits
        long low = bb.getLong();  // Get the least significant 64 bits
        
        return new UUID(high, low);
    }

    private PersonDataView personRowMapper(ResultSet rs, int i) throws SQLException {
        PersonDataView p = new PersonDataView();
        
        // deal with the UUID
        byte[] uuidBytes = rs.getBytes("id");
        UUID uuid = getUuidFromByteArray(uuidBytes);
        
        p.setId(uuid.toString());
        p.setAge(rs.getInt("age"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));

        return p;
    }
    
    private SchoolDataView schoolRowMapper(ResultSet rs, int i) throws SQLException {
        SchoolDataView s = new SchoolDataView();
        s.setId(rs.getString("id"));
        s.setName(rs.getString("name"));
        s.setState(rs.getString("state"));
        s.setType(rs.getString("type"));
        return s;
    }

    private final RowMapper<PersonDataView> personViewRowMapper = (rs, i) -> personRowMapper(rs, i);
    private final RowMapper<SchoolDataView> schoolViewRowMapper = (rs, i) -> schoolRowMapper(rs, i);
    
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void setJdbcTemplate() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void dumpPersonData() {
        String sql = "select * from person_data";
        List<PersonDataView> people = jdbcTemplate.query(sql, personViewRowMapper);
        people.stream()
                .forEach(p -> log.info("read person {}", p));

    }
    
    private void dumpSchoolData() {
        String sql = "select * from school_data";
        List<SchoolDataView> schools = jdbcTemplate.query(sql, schoolViewRowMapper);
        schools.stream()
                .forEach(s -> log.info("read school {}", s));
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("dumping raw data");

        dumpPersonData();
        dumpSchoolData();
    }
}
