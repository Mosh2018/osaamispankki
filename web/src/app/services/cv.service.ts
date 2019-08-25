import { Injectable } from '@angular/core';
import {UserService} from './user.service';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CvService {

  constructor(private userService: UserService) {}


  saveProfile(data) {
    return this.userService.saveCv(data, 'profile')
      .pipe(map( x => {
        return x;
      }));
  }

  getPersonalInformation() {
    return this.userService.getCv('profile');
  }

  saveEducation(data: any) {
    return this.userService.saveCv(data, 'education')
      .pipe(map( x => {
        return x;
      }));
  }

  getEducations() {
    return this.userService.getCv('education')
      .pipe(map( x => {
      return x;
    }));
  }

  delete(id: number) {
    return this.userService.deleteCV('education', id)
      .pipe(map( x => {
        return x;
      }));
  }
}
