import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CompanyListComponent} from "./company-list/company-list.component";
import {EmployeeListComponent} from "./employee-list/employee-list.component";

const routes: Routes = [
  {path: 'companies', component: CompanyListComponent},
  {path: 'employees', component: EmployeeListComponent},
  {path: '', redirectTo: '/companies', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
