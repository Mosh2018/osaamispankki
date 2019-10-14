import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from '../../../allServices/services/authentication.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {
  signUpForm = this.fb.group({
    username: ['', Validators.required],
    firstName: ['', Validators.required],
    surname: ['', Validators.required],
    phoneNo: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    password2: ['', Validators.required]
  });
  errors = null;

  constructor(public employmentDialog: MatDialogRef<UserRegisterComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder,
              private auth: AuthenticationService) { }

  register() {
    this.auth.signUp(this.signUpForm.value).subscribe(data => {
      this.onNoClick(true);
    }, error => {
      this.errors = error;
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
