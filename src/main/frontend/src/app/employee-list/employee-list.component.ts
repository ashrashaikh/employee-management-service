import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../service/employee.service';
import { Employee } from '../models/employee';
import { Router } from '@angular/router';


@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.scss'
})
export class EmployeeListComponent implements OnInit {
  departmentId: string = '';
  employees: Employee[] = [];

  constructor(private employeeService: EmployeeService,
    private router: Router) {}

  ngOnInit(): void {}

  getAllEmployees() {
    if (this.departmentId) {
      this.employeeService.getEmployeesByDepartmentId(this.departmentId)
        .subscribe((response) => {
          this.employees = response;
        });
    }
  }

  viewEmployeeDetails(employeeId: string): void {
    this.router.navigate(['/employee-view', employeeId]);
  }
}
