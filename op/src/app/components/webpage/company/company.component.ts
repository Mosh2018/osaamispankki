import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {
  sidenavElement = [
    {name: 'Information', path: 'information', link: '/company/information'},
    {name: 'Employees', path: 'employees', link: '/company/employees'},
  ];

  constructor() { }

  ngOnInit() {
  }

}
