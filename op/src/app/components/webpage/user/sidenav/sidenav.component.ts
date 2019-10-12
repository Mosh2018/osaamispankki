import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  activeLink = 'Business cards';
  sidenavElement = [
    {name: 'Business cards', path: 'business_card', link: '/user/business_card'},
    {name: 'Personal', path: 'personal', link: '/user/personal'},
    {name: 'Education', path: 'education', link: '/user/education'},
    {name: 'Experience', path: 'experience', link: '/user/experience'}];
  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.activeLink = this.route.snapshot.children[0].routeConfig.path;
  }

  activate(name: string): void {
    this.activeLink = name;
  }

}
