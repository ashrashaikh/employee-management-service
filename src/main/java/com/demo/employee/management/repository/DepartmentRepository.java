package com.demo.employee.management.repository;

import com.demo.employee.management.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Department table
 */
public interface DepartmentRepository extends JpaRepository<Department, String> {
}
