import { Injectable } from '@angular/core';
import {EndpointUserService} from '../../../../allServices/services/endpoint-user.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EducationAndExperienceService {

  constructor(private endpoint: EndpointUserService) { }

  save(str: string, data: any) {
    return this.endpoint.saveCv(data, str)
      .pipe(map( x => {
        return x;
      }));
  }

  get(str: string) {
    return this.endpoint.getCv(str)
      .pipe(map( x => {
        return x;
      }));
  }

  delete(str: string, id: number) {
    return this.endpoint.deleteCV(str, id)
      .pipe(map( x => {
        return x;
      }));
  }

}
