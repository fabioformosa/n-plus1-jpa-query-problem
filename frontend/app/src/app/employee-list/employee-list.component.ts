import { Component } from '@angular/core';
import {Company} from "../company/company";
import {EmployeeService} from "../employee/employee.service";
import {Employee} from "../employee/employee";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent {

  employees: Employee[] | null

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this._loadWithNPlus1Problem();
  }

  private _loadWithNPlus1Problem = () => {
    this.employeeService.fetchAllWithNPlus1Problem().subscribe(
      (paginatedList) =>  this.employees = paginatedList.items
    );
  }

  private _loadWithoutNPlus1Problem = () => {
    this.employeeService.fetchAllWithoutNPlus1Problem().subscribe(
      (paginatedList) =>  this.employees = paginatedList.items
    );
  }

  refetchSlow() {
    this.employees = null;
    this._loadWithNPlus1Problem();
  }
  refetchFast() {
    this.employees = null;
    this._loadWithoutNPlus1Problem();
  }

}
