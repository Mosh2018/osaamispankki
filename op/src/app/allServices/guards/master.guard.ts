import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {JwtService} from '../services/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class MasterGuard implements CanActivate {
  constructor(private jwtService: JwtService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.jwtService.currentUserValue !== null && !this.jwtService.isExpired()) {
      console.log(this.jwtService.getUserFullInformation(), 'Full information of user');
      return true;
    }
    return false;
  }
}
