import { Component, OnInit } from '@angular/core';
import {UserLoginComponent} from '../user-login/user-login.component';
import {valid} from '../../../allServices/utils/useful-functions';
import {DialogService} from '../../../allServices/services/dialog.service';
import {Router} from '@angular/router';
import {screenSize} from '../../../allServices/utils/global';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent {
  constructor(private dialogService: DialogService) { }
  openEmploymentLoginDialog() {
   this.dialogService.openEmploymentLogin();
  }
}
