import { Injectable } from '@angular/core';
import {EndpointUserService} from '../../../../allServices/services/endpoint-user.service';
import {map} from 'rxjs/operators';
import {byteToImage} from '../../../../allServices/utils/global';

@Injectable({
  providedIn: 'root'
})
export class UserAndCompanyService {

  constructor(private endpoint: EndpointUserService) { }


  uploadFile(formatData: FormData, uploadPhoto: string) {
    return this.endpoint.uploadFile(formatData, uploadPhoto)
      .pipe(map( stream => {
        return byteToImage(stream);
      }));
  }

  getPersonalImage() {
    return this.endpoint.getImage('download_photo')
      .pipe(map( stream => {
          return byteToImage(stream);
        })
      );
  }

  deletePersonalPhoto() {
    return this.endpoint.deleteImage('personal-photo')
      .pipe(map( x => {
          return x;
        })
      );
  }

  saveCompany(company: any) {
    return this.endpoint.saveCompany('company', company)
      .pipe(map( x => {
          return x;
        })
      );
  }

  deleteCompany(id: any) {
    return this.endpoint.deleteCompany('company', id)
      .pipe(map( x => {
          return x;
        })
      );
  }

  getCompanies() {
    return this.endpoint.getCompanies('companies')
      .pipe(map( x => {
          return x;
        })
      );
  }

  getValidations() {
    return this.endpoint.getUserValidations()
      .pipe(map( x => {
        return x;
      })
    );
  }
}
