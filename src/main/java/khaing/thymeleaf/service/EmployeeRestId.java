package khaing.thymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRestId {
     private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRestId(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void resetEmployeeId() {
        jdbcTemplate.update("SET @new_id := 0");
        jdbcTemplate.update("UPDATE employee SET id = @new_id:= @new_id + 1");
        jdbcTemplate.update("ALTER TABLE employee AUTO_INCREMENT = 1");
    }
}
