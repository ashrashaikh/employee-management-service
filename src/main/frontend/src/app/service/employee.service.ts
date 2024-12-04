import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee';
import { environment } from '../../environments/environment';
import { APIEndpoints } from '../shared/api-endpoints';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = `${environment.baseUrl}`;

  constructor(private httpClient: HttpClient) { }

  getEmployeesByDepartmentId(departmentId: string): Observable<Employee[]> {
    const employeesByDepartmentIdApiUrl = `${this.baseUrl}${APIEndpoints.departments}/${departmentId}/employees`;
    return this.httpClient.get<Employee[]>(employeesByDepartmentIdApiUrl, {responseType: 'json'});
  }

  getEmployeeById(employeeId: string): Observable<Employee> {
    const employeeByIdApiUrl =
      `${this.baseUrl}/employees/${employeeId}`;
    return this.httpClient.get<Employee>(employeeByIdApiUrl);
  }

  updateEmployee(employeeId: string, employee: Employee): Observable<any> {
    return this.httpClient.put(`${this.baseUrl}/employees/${employeeId}`, employee);
  }

  deleteEmployee(employeeId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.baseUrl}/employees/${employeeId}/delete`);
  }
}
