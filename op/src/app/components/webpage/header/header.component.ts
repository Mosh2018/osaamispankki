import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material';
import {screenSize} from '../../../allServices/utils/global';
import {UserLoginComponent} from '../../login/user-login/user-login.component';
import {JwtService} from '../../../allServices/services/jwt.service';
import {Router} from '@angular/router';
import {isTrue, valid} from '../../../allServices/utils/useful-functions';
import {UserRegisterComponent} from '../../login/user-register/user-register.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public dialog: MatDialog,
              private router: Router,
              private jwtService: JwtService) { }
  screenSize = { width: 500, height: 500 };
  tab = 0;

  ngOnInit() {
    this.screenSize = screenSize();
  }

  openEmploymentLoginDialog() {
    const employmentDialog = this.dialog.open(UserLoginComponent, {
      width: (this.screenSize.width / 0.8) + 'px',
      height: (this.screenSize.height / 0.5) + 'px',
      maxWidth: '1000px',
      maxHeight: '800px',
      minWidth: '500px',
      minHeight: '500px',
      data: {}
    });

    employmentDialog.afterClosed().subscribe(result => {
      if (valid(result) && result.valid) {
        this.router.navigate(['/user']).then(r => {
          console.log(r);
        });
      }
    });
  }

  openEmploymentRegisterDialog() {
    const employmentDialog = this.dialog.open(UserRegisterComponent, {
      width: (this.screenSize.width / 0.8) + 'px',
      height: (this.screenSize.height / 0.5) + 'px',
      maxWidth: '1000px',
      maxHeight: '800px',
      minWidth: '500px',
      minHeight: '500px',
      data: {}
    });

    employmentDialog.afterClosed().subscribe(result => {
      if (valid(result) && result.valid) {
        this.router.navigate(['/success']).then(r => {
          console.log(r);
        });
      }
    });
  }

  isHidden(el) {
    console.log(document.getElementById(el))
    return document.getElementById(el).hidden;
  }
  get logged() { return this.jwtService.getJWT() && !this.jwtService.isExpired(); }

  logout() {
    this.jwtService.logout();
    this.router.navigate(['/main']);
  }
}
