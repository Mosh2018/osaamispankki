import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {notEmpty, valid} from '../../utils/useful-functions';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  activeLink = '';
  @Input()
  sidenavElement = [];

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.activeLink = this.route.snapshot.children[0].routeConfig.path;
    if (!notEmpty(this.activeLink)) {
      this.activeLink = this.sidenavElement[0].path;
    }
  }

  activate(name: string): void {
    this.activeLink = name;
  }

}
