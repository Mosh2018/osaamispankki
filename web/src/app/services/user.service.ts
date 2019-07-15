import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  configUrl = 'assets/config.json';
  userURL =  this.configUrl + 'api/user/login';
  userURL2 =  'http://localhost:8080/api/user/login';
  constructor(private http: HttpClient) {}

  login(info: any) {
    return this.http.post(this.userURL2, info);
  }
}
