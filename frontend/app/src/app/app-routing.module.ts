import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CompanyListComponent} from "./company-list/company-list.component";

const routes: Routes = [
  {path: 'companies', component: CompanyListComponent},
  {path: '', redirectTo: '/companies', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
