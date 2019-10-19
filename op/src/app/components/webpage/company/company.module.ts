import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CompanyRoutingModule} from './company-routing.module';
import {CompanyComponent} from './company.component';
import {EmployeesComponent} from './employees/employees.component';
import {SharedModule} from '../../../shared.module';
import { CompanyInfoComponent } from './company-info/company-info.component';
import {RegisterService} from './services/register.service';
import {CompanyAdminService} from './services/company-admin.service';


@NgModule({
  declarations: [
    CompanyComponent,
    EmployeesComponent,
    CompanyInfoComponent],
  imports: [
    CommonModule,
    CompanyRoutingModule,
    SharedModule
  ],
  exports: [

  ],
  entryComponents: [

  ],
  providers: [
    RegisterService,
    CompanyAdminService,
  ]
})
export class CompanyModule { }
