import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-sign',
  templateUrl: './sign.component.html',
  styleUrls: ['./sign.component.less']
})
export class SignComponent {

  signUpForm = this.fb.group({
    username: ['', Validators.required],
    firstName: ['', Validators.required],
    surname: ['', Validators.required],
    phoneNo: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    password2: ['', Validators.required],
    company: ['']
  });

  formSettings = {
    username: false,
    firstName: false,
    surname: false,
    phoneNo: false,
    email: false,
    password: false,
    password2: false,
    company: false,
    progressing: false,
    submit: true,
    progressValue: 0
  };

  errors = null;
  constructor(private fb: FormBuilder,
              private auth: AuthenticationService) { }

  register() {
    this.formSettings.submit = false;
    this.formSettings.progressing = true;
    this.auth.signUp(this.signUpForm.value)
      .subscribe( user =>  {
        console.log('result', user)
        setInterval(()  => {
          this.formSettings.progressValue = this.formSettings.progressValue + 5;
          if (this.formSettings.progressValue > 99) {
            location.reload();
          }
        }, 100);
      }, error1 => {
        console.log('erroooooooo',  error1)
        this.errors = error1;
        this.formSettings.submit = true;
        this.formSettings.progressing = false;

        console.log('errors', error1);
      });
  }

  isFocus(variable: string) {
    const outOfLoop = ['submit', 'progressing', 'progressValue'];
    this.formSettings[variable] = true;
    // tslint:disable-next-line:forin
    for (const key in this.formSettings) {
      if (outOfLoop.indexOf(key) < 0) {
        if (key !== variable) {
          this.formSettings[key] = false;
        } else {
          this.formSettings[key] = true;
        }
      }
    }
  }


  getError(filed: string) {

    if (this.errors && this.errors[filed]) {
      console.log(filed, this.errors[filed]);
      return this.errors[filed];
    }
    return null;
  }
}
