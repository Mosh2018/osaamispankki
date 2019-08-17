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
        console.log(x);
        return x;
      }));
  }

  getPersonalInformation() {
    return this.userService.getCv('profile');
  }
}
