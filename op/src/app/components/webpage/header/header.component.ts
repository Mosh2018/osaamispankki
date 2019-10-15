import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material';
import {screenSize} from '../../../allServices/utils/global';
import {UserLoginComponent} from '../../login/user-login/user-login.component';
import {JwtService} from '../../../allServices/services/jwt.service';
import {Router} from '@angular/router';
import {isTrue, valid} from '../../../allServices/utils/useful-functions';
import {UserRegisterComponent} from '../../login/user-register/user-register.component';
import {DialogService} from '../../../allServices/services/dialog.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public dialog: MatDialog,
              private dialogService: DialogService,
              private router: Router,
              private jwtService: JwtService) { }
  screenSize = { width: 500, height: 500 };
  tab = 0;

  ngOnInit() {
    this.screenSize = screenSize();
  }

  openEmploymentLoginDialog() {
    this.dialogService.openEmploymentLogin();
  }

  openCompanyLoginDialog() {
    this.dialogService.openCompanyLogin();
  }

  openEmploymentRegisterDialog() {
    this.dialogService.openEmploymentRegister();
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
