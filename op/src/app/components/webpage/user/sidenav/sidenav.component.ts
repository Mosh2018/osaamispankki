import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  activeLink = 'Business cards';
  sidenavElement = [
    {name: 'Business cards', link: '/user/business_card'},
    {name: 'Personal', link: '/user/personal'},
    {name: 'Education', link: '/user/education'},
    {name: 'Experience', link: '/user/experience'}];
  constructor() { }

  ngOnInit() {
  }

  activate(name: string): void {
    this.activeLink = name;
  }

}
