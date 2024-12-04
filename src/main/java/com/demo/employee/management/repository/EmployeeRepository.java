package com.demo.employee.management.repository;

import com.demo.employee.management.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Employee table
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
