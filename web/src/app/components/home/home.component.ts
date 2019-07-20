import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {
  public now = 0;
  constructor(private auth: AuthenticationService) { }

  ngOnInit() {
  }

  getUserFullName() {
    return this.auth.getUserFullInformation().name;
  }

}
