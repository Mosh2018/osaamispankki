import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {

  constructor(private auth: AuthenticationService,
              private router: Router) { }

  ngOnInit() {
    if (!this.auth.getJWT() && this.auth.isExpired()) {
      this.router.navigate(['/log']);
    }
  }

  getUserFullName() {
    return this.auth.getUserFullInformation().name;
  }
}
