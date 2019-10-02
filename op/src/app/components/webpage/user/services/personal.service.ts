import { Injectable } from '@angular/core';
import {EndpointService} from '../../../../allServices/services/endpoint.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PersonalService {

  constructor(private endpoint: EndpointService) { }

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
