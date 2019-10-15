import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  sidenavElement = [
    {name: 'Business cards', path: 'business_card', link: '/user/business_card'},
    {name: 'Personal', path: 'personal', link: '/user/personal'},
    {name: 'Education', path: 'education', link: '/user/education'},
    {name: 'Experience', path: 'experience', link: '/user/experience'}];

  constructor() { }

  ngOnInit() {
  }

}
