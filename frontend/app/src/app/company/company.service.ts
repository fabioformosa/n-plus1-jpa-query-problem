import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Company} from "./company";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private httpClient: HttpClient) { }

  fetchAllWithNPlus1Problem(): Observable<any> {
    return this.httpClient.get('/api/companies/slow');
  }

  fetchAllWithoutNPlus1Problem(): Observable<any> {
    return this.httpClient.get('/api/companies/fast');
  }

}
