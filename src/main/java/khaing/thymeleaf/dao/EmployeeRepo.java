package khaing.thymeleaf.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import khaing.thymeleaf.entity.EmployeeEntity;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {

}

