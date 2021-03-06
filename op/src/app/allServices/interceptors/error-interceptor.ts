import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptor implements HttpInterceptor {

  constructor() { }
  // todo: improve it
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      // tslint:disable-next-line:only-arrow-functions
      catchError(err => {
          if (err.status === 401) {
            // this.auth.logout();
            // location.reload();
          }
          return throwError(err.error);
        }));
  }

}
