import { Injectable } from '@angular/core';
import {EndpointUserService} from '../../../../allServices/services/endpoint-user.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PersonalService {

  constructor(private endpoint: EndpointUserService) { }

  getPersonalInformation() {
    return this.endpoint.getPersonalInformation('profile');
  }

  savePersonalInformation(data) {
    return this.endpoint.savePersonalInformation(data, 'profile')
      .pipe(map( x => {
        return x;
      }));
  }
}
