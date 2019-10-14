import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {X_URL} from '../utils/global';

@Injectable({
  providedIn: 'root'
})
export class EndpointService {

  private userURL2 =  'http://localhost:8080/api';

  private USER = '/user/';
  private CV = '/basic/';
  private PHOTO = '/photo/';
  private COMPANY = '/userAndCompany/';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(environment.url + X_URL.LOGIN, data);
  }
  signUp(data: any) {
    return this.http.post(environment.url + X_URL.REGISTER, data);
  }
  logout() {
    return this.http.get(environment.url + X_URL.LOGOUT);
  }

  // personal information
  savePersonalInformation(data: any, item: string) {
    return this.http.post(this.userURL2 + this.CV + item, data);
  }

  getPersonalInformation(item: string) {
    return this.http.get(this.userURL2 + this.CV + item);
  }

  deletePersonalInformation(item: string, id: number) {
    return this.http.delete(this.userURL2 + this.CV + item + '/' + id);
  }

  // images and files
  uploadFile(data: any, item: string) {
    return this.http.post(this.userURL2 + this.PHOTO + item, data, {responseType: 'arraybuffer'});
  }

  getImage(item: string) {
    return this.http.get(this.userURL2 + this.PHOTO + item , {responseType: 'arraybuffer'});
  }

  deleteImage(item: string) {
    return this.http.delete(this.userURL2 + this.PHOTO + item);
  }

  // user company
  saveCompany(item: string, data: any) {
    return this.http.post(this.userURL2 + this.COMPANY + item, data);
  }

  getCompanies(item: string) {
    return this.http.get(this.userURL2 + this.COMPANY + item);
  }

  deleteCompany(item: string, id: any) {
    return this.http.delete(this.userURL2 + this.COMPANY + item + '/' + id);
  }

  // education and experience
  saveCv(data: any, item: string) {
    return this.http.post(this.userURL2 + this.CV + item, data);
  }

  getCv(item: string) {
    return this.http.get(this.userURL2 + this.CV + item);
  }

  editCV(item: string, data: any) {
    return this.http.put(this.userURL2 + this.CV + item, data);
  }

  deleteCV(item: string, id: number) {
    return this.http.delete(this.userURL2 + this.CV + item + '/' + id);
  }

  // commons
  getUserValidations() {
    return this.http.get(this.userURL2 + this.USER + 'validations');
  }
}
