import { Injectable } from '@angular/core';
import {EndpointCompanyService} from '../../../../allServices/services/endpoint-company.service';
import {map} from 'rxjs/operators';
import {p} from '../../../../allServices/utils/global';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private endpoint: EndpointCompanyService) { }

  register(yTunnus: string, activationCode: string) {
    return this.endpoint.registerCompany(yTunnus, activationCode).pipe(map( x => {
      return x;
      }));
  }

  getRegisteredCompany() {
    return p(this.endpoint.registeredCompany());
  }

  getRegisteredCompanies() {
    return p(this.endpoint.getRegisteredCompanies());
  }
}
