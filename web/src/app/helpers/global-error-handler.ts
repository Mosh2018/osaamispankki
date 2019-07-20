import {ErrorHandler, Injectable} from '@angular/core';
import {AuthenticationService} from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler{

  constructor(private auth: AuthenticationService) { }

  handleError(error: any): void {
    console.log('errors from global', error);
    // this.auth.logout();
    // location.reload();
  }
}
