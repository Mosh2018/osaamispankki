import {Injectable} from '@angular/core';
import {JwtResponse, localStorageKey} from '../utils/global';
import {map} from 'rxjs/operators';
import {JwtService} from './jwt.service';
import {EndpointUserService} from './endpoint-user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private endpoint: EndpointUserService,
              private jwtServer: JwtService) {
  }

  login(loginRequest: any) {
    return this.endpoint.login(loginRequest)
      .pipe(map((response: JwtResponse) => {
        // login successful if there's a jwt token in the response
        if (response && response.success) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem(localStorageKey(), response.jwt);
          this.jwtServer.currentUserSubject.next(response.jwt);
        }
        return response;
      }));
  }

  signUp(registerForm: any) {
    return this.endpoint.signUp(registerForm).pipe(map(
      (response: any) => {
        return response;
      }
    ));
  }
}
