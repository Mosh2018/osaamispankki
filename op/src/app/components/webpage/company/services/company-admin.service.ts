import { Injectable } from '@angular/core';
import {EndpointCompanyService} from '../../../../allServices/services/endpoint-company.service';
import {p} from '../../../../allServices/utils/global';

@Injectable({
  providedIn: 'root'
})
export class CompanyAdminService {

  constructor(private endpoint: EndpointCompanyService) { }

  getEmployees() { return p(this.endpoint.getCompanyEmployees()); }
}
