import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {CompanyComponent} from './company.component';
import {EmployeesComponent} from './employees/employees.component';
import {CompanyGuard} from '../../../allServices/guards/company-guard';
import {CompanyInfoComponent} from './company-info/company-info.component';

const routes: Routes = [{ path: '', component: CompanyComponent, children: [
    {path: 'information', component: CompanyInfoComponent, canActivate: [CompanyGuard]},
    {path: 'employees', component: EmployeesComponent, canActivate: [CompanyGuard]},
    {path: '', redirectTo: 'information', pathMatch: 'full', canActivate: [CompanyGuard]}
  ]}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }
