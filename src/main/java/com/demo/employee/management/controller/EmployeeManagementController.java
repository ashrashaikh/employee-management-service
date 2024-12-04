package com.demo.employee.management.controller;

import com.demo.employee.management.domain.Department;
import com.demo.employee.management.domain.Employee;
import com.demo.employee.management.domain.EmployeeDTO;
import com.demo.employee.management.service.EmployeeAndDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * EmployeeManagementController
 * All the APIs are defined in this controller.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeManagementController {
    @Autowired
    private EmployeeAndDepartmentService employeeAndDepartmentService;

    /**
     * API to fetch all the employees
     * @return all the employees
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> fetchAllEmployees() {
        return ResponseEntity.ok(employeeAndDepartmentService.getAllEmployees());
    }

    /**
     * API to fetch all the departments
     * @return all the departments
     */
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> fetchAllDepartments() {
        return ResponseEntity.ok(employeeAndDepartmentService.getAllDepartment());
    }

    /**
     * Get Employee Details By Employee ID
     * @param employeeId employee ID to search
     * @return EmployeeDTO of given employeeId
     */
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> fetchEmployeeById(@PathVariable String employeeId) {
        final Employee employee = employeeAndDepartmentService.getEmployeeById(employeeId);
        System.out.println("Employee: " + employee.getName());
        final EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(),
                employee.getName(), employee.getEmail(), employee.getPosition(), employee.getSalary());
        System.out.println("EmployeeDTO: " + employeeDTO.getName());
        return ResponseEntity.ok(employeeDTO);
    }

    /**
     * Get all employees in a given department
     * @param departmentId department ID
     * @return List of employees in the department
     */
    @GetMapping("/departments/{departmentId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(employeeAndDepartmentService.getEmployeesInDepartment(departmentId));
    }

    /**
     * Add new employee to the department
     * @param departmentId department ID
     * @param employee employee to be added
     * @return new added employee record
     */
    @PostMapping("/departments/{departmentId}/employees")
    public ResponseEntity<Employee> addEmployeeInDepartment(@PathVariable String departmentId,
                                                            @RequestBody Employee employee) {
        final Employee newEmployee = employeeAndDepartmentService.addEmployeeToDepartment(employee, departmentId);
        return ResponseEntity.status(CREATED).body(newEmployee);
    }

    /**
     * Delete Employee
     * @param employeeId employee ID to delete
     */
    @DeleteMapping("/employees/{employeeId}/delete")
    public void deleteEmployee(@PathVariable String employeeId) {
        employeeAndDepartmentService.deleteEmployee(employeeId);
        ResponseEntity.ok("Employee deleted successfully.");
    }

    /**
     * updateEmployee existing employee with given employee data
     * @param employeeId employeeId which needs to be updated
     * @param employeeDTO new employee data
     * @return updateed employeeDto data
     */
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String employeeId,
                                                      @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeAndDepartmentService.updateEmployee(employeeId, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
}
