import { Component, OnInit } from '@angular/core';
import { Employee } from '../models/employee';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../service/employee.service';

@Component({
  selector: 'app-employee-view',
  templateUrl: './employee-view.component.html',
  styleUrl: './employee-view.component.scss'
})
export class EmployeeViewComponent implements OnInit {
  departmentId: string = '';
  employeeId: string = '';
  employee: Employee | null = null;

  constructor(private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    this.departmentId = routeParams.get('departmentId')!;
    this.employeeId = routeParams.get('employeeId')!;
    console.info('Employee Id: ' + this.employeeId);

    this.employeeService.getEmployeeById(this.employeeId)
      .subscribe({
        next: (response) => {
          this.employee = response as Employee;
          console.log('employee: ' + this.employee?.name);
        },
        error: (err) => {
          console.error('Error fetching employee:', err);
        }
      });
  }

  updateEmployee(): void {
    if (this.employee) {
      this.employeeService.updateEmployee(this.employee?.id, this.employee).subscribe({
        next: (updatedEmployee) => {
          console.log('Employee updated successfully!');
          this.router.navigate(['/employee-list']);
        },
        error: (err) => {
          console.error('Error updating employee:', err);
        }
      });
    }
  }

  deleteEmployee(): void {
    if (this.employee) {
      this.employeeService.deleteEmployee(this.employee.id).subscribe(() => {
        alert('Employee deleted');
        this.router.navigate(['/']);
      });
    }
  }

  goBack() {
    this.router.navigate(['/']);
  }

}
