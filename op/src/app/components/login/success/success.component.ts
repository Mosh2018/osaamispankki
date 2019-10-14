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
export class SuccessComponent implements OnInit {

  screenSize = {width: 500, height: 500};
  constructor(private dialog: DialogService,
              private router: Router) { }

  ngOnInit() {
    this.screenSize = screenSize();
  }

  openEmploymentLoginDialog() {
    this.dialog.openDialog({
      component: UserLoginComponent,
      data: {},
      width: (this.screenSize.width / 0.8) + 'px',
      height: (this.screenSize.height / 0.8) + 'px',
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

  gotoMainPage() {
    this.router.navigateByUrl('/');
  }
}
