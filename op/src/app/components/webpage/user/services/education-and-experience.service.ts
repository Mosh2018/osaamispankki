import { Injectable } from '@angular/core';
import {EndpointService} from '../../../../allServices/services/endpoint.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EducationAndExperienceService {

  constructor(private endpoint: EndpointService) { }

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
