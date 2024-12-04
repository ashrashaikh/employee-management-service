import { Employee } from "./employee";

export interface Department {
    id: string;
    name: string;
    location: string;
    employees: Employee[];
}