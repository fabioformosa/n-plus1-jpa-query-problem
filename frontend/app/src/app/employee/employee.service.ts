import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private httpClient: HttpClient) { }

  fetchAllWithNPlus1Problem(): Observable<any> {
    return this.httpClient.get('/api/employees/slow');
  }

  fetchAllWithoutNPlus1Problem(): Observable<any> {
    return this.httpClient.get('/api/employees/fast');
  }
}
