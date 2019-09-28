import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {AuthenticationService} from '../../../allServices/services/authentication.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  hide = true;
  errors = null;
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  constructor(public employmentDialog: MatDialogRef<UserLoginComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder,
              private auth: AuthenticationService) {}

  ngOnInit() {
  }

  onSubmit() {
    this.auth.login(this.loginForm.value)
      .pipe(first())
      .subscribe(
        () => {
          this.onNoClick(true);
        },
        error1 => {
          this.errors = error1;
          this.loginForm.reset();
        });
  }

  onNoClick(isValid = false) {
    this.employmentDialog.close({valid: isValid});
  }

  getError(filed: string) {
    if (this.errors && this.errors[filed]) {
      return this.errors[filed];
    }
    return null;
  }
}
