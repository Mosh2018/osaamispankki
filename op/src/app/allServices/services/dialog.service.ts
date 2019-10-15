import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {map} from 'rxjs/operators';
import {UserLoginComponent} from '../../components/login/user-login/user-login.component';
import {valid} from '../utils/useful-functions';
import {Router} from '@angular/router';
import {UserRegisterComponent} from '../../components/login/user-register/user-register.component';

export interface DialogData {
  width: string;
  height: string;
  maxWidth: string;
  minWidth: string;
  maxHeight: string;
  minHeight: string;
  component: any;
  data: any;
}

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  private screenSizeOfLogin = { width: 500, height: 500 };
  private screenSizeOfRegister = { width: 500, height: 500 };

  constructor(public dialog: MatDialog,
              private router: Router) { }

  openEmploymentLogin() {
    this.openDialog({
      component: UserLoginComponent,
      data: {title: 'Login as user'},
      width: (this.screenSizeOfLogin.width / 0.8) + 'px',
      height: (this.screenSizeOfLogin.height / 0.8) + 'px',
      maxWidth: '1000px',
      maxHeight: '800px',
      minWidth: '500px',
      minHeight: '500px',
    }).subscribe(result => {
      if (valid(result) && result.valid) {
        this.router.navigate(['/user']).then(r => {
          console.log(r);
        });
      }
    });
  }

  openCompanyLogin() {
    this.openDialog({
      component: UserLoginComponent,
      data: {title: 'Register as company'},
      width: (this.screenSizeOfLogin.width / 0.8) + 'px',
      height: (this.screenSizeOfLogin.height / 0.8) + 'px',
      maxWidth: '1000px',
      maxHeight: '800px',
      minWidth: '500px',
      minHeight: '500px',
    }).subscribe(result => {
      if (valid(result) && result.valid) {
        this.router.navigate(['/company']).then(r => {
          console.log(r);
        });
      }
    });
  }

  openEmploymentRegister() {
    const employmentDialog = this.dialog.open(UserRegisterComponent, {
      width: (this.screenSizeOfRegister.width / 0.8) + 'px',
      height: (this.screenSizeOfRegister.height / 0.5) + 'px',
      maxWidth: '1000px',
      maxHeight: '800px',
      minWidth: '500px',
      minHeight: '500px',
      data: {title: 'Register as user'}
    });

    employmentDialog.afterClosed().subscribe(result => {
      if (valid(result) && result.valid) {
        this.router.navigate(['/success']).then(r => {
          console.log(r);
        });
      }
    });
  }

  private openDialog(dialog: DialogData) {
    const employmentDialog = this.dialog.open(dialog.component, {
      width: dialog.width,
      height: dialog.height,
      maxWidth: dialog.maxWidth,
      data: dialog.data
    });

    return employmentDialog.afterClosed().pipe(map( x => {
      return x;
    }));
  }
}
