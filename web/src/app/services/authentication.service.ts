import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {UserService} from './user.service';
import {map} from 'rxjs/operators';
import {JwtResponse} from '../../models/jwtResponse';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<string>;
  public currentUser: Observable<string>;
  key = 'currentUser';
  constructor(private userService: UserService,
              private jwtHelper: JwtHelperService) {
    this.currentUserSubject = new BehaviorSubject<string>(localStorage.getItem(this.key));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): string {
    return this.currentUserSubject.value;
  }

  login(loginRequest: any) {
    return this.userService.login(loginRequest)
      .pipe(map((response: JwtResponse) => {
        // login successful if there's a jwt token in the response
        if (response && response.success) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem(this.key, response.jwt);
          this.currentUserSubject.next(response.jwt);
        }
        return response;
      }));
  }

  signUp(newUser: any) {
    return this.userService.signUp(newUser);
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem(this.key);
    this.currentUserSubject.next(null);
  }

  getUserFullInformation() {
    return this.jwtHelper.decodeToken(this.currentUserValue);
  }
  getExpirationDay() {
     return this.jwtHelper.getTokenExpirationDate(this.currentUserValue);
  }

  isExpired() {
      return this.jwtHelper.isTokenExpired(this.currentUserValue);
  }

  getJWT() {
    return this.jwtHelper.decodeToken(localStorage.getItem(this.key));
  }

}
