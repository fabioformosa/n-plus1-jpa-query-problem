import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../company/company.service";
import {Company} from "../company/company";
import {Observable} from "rxjs";

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.scss']
})
export class CompanyListComponent implements OnInit{

  companies: Company[] | null
  totalItems: number;
  totalPages: number;

  constructor(private companyService: CompanyService) {
  }

  ngOnInit(): void {
    this._loadWithNPlus1Problem();
  }

  private _loadWithNPlus1Problem = () => {
    this.companyService.fetchAllWithNPlus1Problem().subscribe(
      (paginatedList) =>  {
        this.companies = paginatedList.items;
        this.totalItems = paginatedList.totalItems;
        this.totalPages = paginatedList.totalPages;
      }
    );
  }

  private _loadWithoutNPlus1Problem = () => {
    this.companyService.fetchAllWithoutNPlus1Problem().subscribe(
      (paginatedList) =>  {
        this.companies = paginatedList.items;
        this.totalItems = paginatedList.totalItems;
        this.totalPages = paginatedList.totalPages;
      }
    );
  }

  refetchSlow() {
    this.companies = null;
    this._loadWithNPlus1Problem();
  }
  refetchFast() {
    this.companies = null;
    this._loadWithoutNPlus1Problem();
  }
}
