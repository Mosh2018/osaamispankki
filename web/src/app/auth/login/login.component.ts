import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {JwtResponse} from '../../../models/jwtResponse';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  sub: Subscription;
  formSetting = {
    input1: false,
    input2: false,
    submit: true,
    reset: false,
    error: false,
    progressing: false,
    progressValue : 0
  };

  errors = null;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router) {}

  ngOnInit() {

  }
  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  onSubmit() {
    this.formSetting.submit = false;
    this.formSetting.progressing = true;
    this.formSetting.reset = false;

    this.sub = this.userService.login(this.loginForm.value).subscribe( (reponse: JwtResponse) => {
      console.log(reponse, 'response');
      setInterval(()  => {
          this.formSetting.progressValue = this.formSetting.progressValue + 5;
          if (this.formSetting.progressValue > 99) {
            this.router.navigate(['/home']);
          }
        }, 100);
      },
      error1 => {
      console.log(error1, 'error throwing');
      this.errors = error1;
      this.formSetting.progressing = false;
      setTimeout(() => {
        console.log( 'moi oio ');
        this.errors = false;
        this.loginForm.reset();
        this.formSetting.submit = true;
        this.formSetting.reset = true;
      }, 2000);
    });
  }

  resolveIssue() {
    this.router.navigate(['/9999']);
  }
}
