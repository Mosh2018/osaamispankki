import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EndpointCompanyService {

  private userURL2 =  environment.url;
  private COMPANY = '/api/company/';
  constructor(private http: HttpClient) { }

  registerCompany(yTunnus: string ) {
    return this.http.get(this.userURL2 + this.COMPANY + yTunnus);
  }

  registeredCompany() {
    return this.http.get(this.userURL2 + this.COMPANY + 'createdPerson');
  }
}
