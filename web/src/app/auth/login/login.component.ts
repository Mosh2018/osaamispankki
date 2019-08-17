import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent {

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  formSetting = {
    input1: false,
    input2: false,
    submit: true,
    reset: false,
    error: false,
    progressing: false,
    progressValue : 0,
    errorCross: false
  };

  errors = null;

  constructor(
    private auth: AuthenticationService,
    private fb: FormBuilder,
    private router: Router) {}

  onSubmit() {
    this.auth.logout();
    this.formSetting.submit = false;
    this.formSetting.progressing = true;
    this.formSetting.reset = false;
    this.auth.login(this.loginForm.value)
      .pipe(first())
      .subscribe(
        () => {
          setInterval(()  => {
            this.formSetting.progressValue = this.formSetting.progressValue + 5;
            if (this.formSetting.progressValue > 99) {
              location.reload();
            }
          }, 100);
        },
        error1 => {
          this.formSetting.errorCross = true;
          this.errors = error1;
          console.log( 'moi oio ', error1);
          setTimeout(() => {
            this.loginForm.reset();
            this.formSetting.submit = true;
            this.formSetting.reset = true;
            this.formSetting.progressing = false;
            this.formSetting.errorCross = false;
          }, 2000);
        });
  }

  resolveIssue() {
    this.router.navigate(['/9999']);
  }

  getError(filed: string) {
    if (this.errors && this.errors[filed]) {
      return this.errors[filed];
    }
    return null;
  }
}
