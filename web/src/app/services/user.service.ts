import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userURL2 =  'http://localhost:8080/api';

  private USER = '/user/';
  private CV = '/basic/';

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
}
