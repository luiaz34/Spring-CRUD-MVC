package khaing.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import khaing.thymeleaf.entity.EmployeeEntity; // Assuming your Employee class is in the model package

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {
    
}

