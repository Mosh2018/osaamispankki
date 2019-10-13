import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {BehaviorSubject, Observable} from 'rxjs';
import {localStorageKey} from '../utils/global';
import {AuthenticationService} from './authentication.service';
import {LoginService} from './login.service';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  public currentUserSubject: BehaviorSubject<string>;
  public currentUser: Observable<string>;
  constructor(private loginService: LoginService,
              private jwtHelper: JwtHelperService) {
    this.currentUserSubject = new BehaviorSubject<string>(localStorage.getItem(localStorageKey()));
    this.currentUser = this.currentUserSubject.asObservable();
  }
  public get currentUserValue(): string {
    return this.currentUserSubject.value;
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
    return this.jwtHelper.decodeToken(localStorage.getItem(localStorageKey()));
  }

  logout() {
    localStorage.removeItem(localStorageKey());
    this.loginService.logout().subscribe( data => console.log(data));
  }
}
