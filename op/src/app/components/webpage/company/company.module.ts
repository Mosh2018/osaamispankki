import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CompanyRoutingModule} from './company-routing.module';
import {CompanyComponent} from './company.component';
import {EmployeesComponent} from './employees/employees.component';
import {SharedModule} from '../../../shared.module';


@NgModule({
  declarations: [
    CompanyComponent,
    EmployeesComponent],
  imports: [
    CommonModule,
    CompanyRoutingModule,
    SharedModule
  ],
  exports: [

  ],
  entryComponents: [

  ]
})
export class CompanyModule { }
