import { Component, OnInit } from '@angular/core';
import {CompanyAdminService} from '../services/company-admin.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {
  private admin: string;

  constructor(private adminService: CompanyAdminService) { }

  ngOnInit() {
    this.adminService.getEmployees().subscribe(x => console.log(x));
  }

}
