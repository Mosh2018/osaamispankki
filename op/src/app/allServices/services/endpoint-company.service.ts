import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EndpointCompanyService {

  private serverUrl =  environment.url;
  private COMPANY = '/api/company/';
  private COMPANY_ADMIN = '/endpoint/company/';
  constructor(private http: HttpClient) { }

  registerCompany(yTunnus: string, activationCode) {
    return this.http.get(this.serverUrl + this.COMPANY + yTunnus + '/code/' + activationCode);
  }

  registeredCompany() {
    return this.http.get(this.serverUrl + this.COMPANY + 'createdPerson');
  }
  // company admin

  getCompanyEmployees() {
    return this.http.get(this.serverUrl + this.COMPANY_ADMIN + 'employees');
  }

  getRegisteredCompanies() {
    return this.http.get(this.serverUrl + this.COMPANY + 'companies');
  }

}
