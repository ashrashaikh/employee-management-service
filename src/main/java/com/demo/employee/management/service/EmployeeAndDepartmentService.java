package com.demo.employee.management.service;

import com.demo.employee.management.domain.Department;
import com.demo.employee.management.domain.Employee;
import com.demo.employee.management.domain.EmployeeDTO;
import com.demo.employee.management.repository.DepartmentRepository;
import com.demo.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeAndDepartmentService
 */
@Service
public class EmployeeAndDepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get all employees
     * @return list of all the employees
     */
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> new EmployeeDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getPosition(),
                        employee.getSalary()))
                .toList();
    }

    /**
     * Get all departments
     * @return list of all the departments
     */
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    /**
     * getEmployeeById
     * @param employeeId employee ID to find
     * @return Employee with given ID.
     */
    public Employee getEmployeeById(final String employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found for Id: " + employeeId));
    }

    /**
     * Get employees in a department
     * @param departmentId departmentId
     * @return list of employees in a given department
     */
    public List<EmployeeDTO> getEmployeesInDepartment(final String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("No department found with the Id: " + departmentId));

        return department.getEmployees()
                .stream()
                .map(employee -> new EmployeeDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getPosition(),
                        employee.getSalary()))
                .collect(Collectors.toList());
    }

    /**
     * Add new Employee in the given department
     * @param employee Employee to be added
     * @param departmentId DepartmentId where the Employee to be added
     * @return Employee
     */
    @Transactional
    public Employee addEmployeeToDepartment(final Employee employee, final String departmentId) {
        final Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("No department found with the Id: " + departmentId));
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee in a department.
     * @param employeeId employeeId to be deleted
     */
    @Transactional
    public void deleteEmployee(final String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found with Id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public EmployeeDTO updateEmployee(final String employeeId, final EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found for Id: " + employeeId));
        existingEmployee.setId(employeeDTO.getId());
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setPosition(employeeDTO.getPosition());
        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return new EmployeeDTO(updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getPosition(),
                updatedEmployee.getSalary());
    }
}
