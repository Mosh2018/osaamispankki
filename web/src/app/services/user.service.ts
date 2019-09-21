import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userURL2 =  'http://localhost:8080/api';

  private USER = '/user/';
  private CV = '/basic/';
  private PHOTO = '/photo/';
  private COMPANY = '/userAndCompany/';

  constructor(private http: HttpClient) {}

  login(data: any) {

    return this.http.post(this.userURL2 + this.USER + 'login', data);
  }

  signUp(data: any) {
    return this.http.post(this.userURL2 + this.USER + 'add_new_user', data);
  }

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
  uploadFile(data: any, item: string) {
    return this.http.post(this.userURL2 + this.PHOTO + item, data, {responseType: 'arraybuffer'});
  }

  getImage(item: string) {
    return this.http.get(this.userURL2 + this.PHOTO + item , {responseType: 'arraybuffer'});
  }

  deleteImage(item: string) {
    return this.http.delete(this.userURL2 + this.PHOTO + item);
  }

  saveCompany(item: string, data: any) {
    return this.http.post(this.userURL2 + this.COMPANY + item, data);
  }

  getCompanies(item: string) {
    return this.http.get(this.userURL2 + this.COMPANY + item);
  }

  getUserValidations() {
    return this.http.get(this.userURL2 + this.USER + 'validations');
  }

  deleteCompany(item: string, id: any) {
    return this.http.delete(this.userURL2 + this.COMPANY + item + '/' + id);
  }
}
