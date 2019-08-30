import { Injectable } from '@angular/core';
import {UserService} from './user.service';
import {map} from 'rxjs/operators';
import {byteToImage} from '../helpers/utils.methods';

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

  deleteEducation(id: number) {
    return this.userService.deleteCV('education', id)
      .pipe(map( x => {
        return x;
      }));
  }

  getExperiences() {
    return this.userService.getCv('experience')
      .pipe(map( x => {
        return x;
      }));
  }

  deleteExperience(id: number) {
    return this.userService.deleteCV('experience', id)
      .pipe(map( x => {
        return x;
      }));
  }

  saveExperience(data: any) {
    return this.userService.saveCv(data, 'experience')
      .pipe(map( x => {
        return x;
      }));
  }

  uploadFile(formatData: FormData, uploadPhoto: string) {
    return this.userService.uploadFile(formatData, uploadPhoto)
      .pipe(map( stream => {
        return byteToImage(stream);
      }));
  }

  getPersonalImage() {
    return this.userService.getImage('download_photo').pipe(
      map( stream => {
        return byteToImage(stream);
      })
    );
  }

  deletePersonalPhoto() {
    return this.userService.deleteImage('personal-photo').pipe(
      map( x => {
        return x;
      })
    );
  }
}
