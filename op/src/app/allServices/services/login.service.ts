import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {X_URL} from '../utils/global';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {}

  login(data: any) {
    console.log(environment.url + X_URL.LOGIN, 'URL');
    return this.http.post(environment.url + X_URL.LOGIN, data);
  }
  signUp(data: any) {
    return this.http.post(environment.url + X_URL.REGISTER, data);
  }
  logout() {
    return this.http.get(environment.url + X_URL.LOGOUT); // add and implement to backend
  }
}
