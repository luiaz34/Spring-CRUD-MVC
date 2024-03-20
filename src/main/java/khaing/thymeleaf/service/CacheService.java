package khaing.thymeleaf.service;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import khaing.thymeleaf.dao.EmployeeRepo;
import khaing.thymeleaf.entity.EmployeeEntity;

@Service
public class CacheService {
    private EmployeeRepo employeeRepo;

    public CacheService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Cacheable(value = "employees")
    public List<EmployeeEntity> findAllEmployees() {
        return employeeRepo.findAll();
    }
}
