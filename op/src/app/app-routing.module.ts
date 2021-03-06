import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserModule} from './components/webpage/user/user.module';
import {SuccessComponent} from './components/login/success/success.component';
import {MainPageComponent} from './components/webpage/main-page/main-page.component';
import {CompanyModule} from './components/webpage/company/company.module';


const routes: Routes = [
  {path: 'main', component: MainPageComponent},
  {path: 'success', component: SuccessComponent},
  {path: 'user',
    loadChildren: () => import(`./components/webpage/user/user.module`).then( m => m.UserModule)},
  {path: 'master-admin-master',
    loadChildren: () => import(`./components/webpage/master/master.module`).then( m => m.MasterModule)},
  { path: 'company',
    loadChildren: () => import('./components/webpage/company/company.module').then(m => m.CompanyModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, UserModule, CompanyModule]
})
export class AppRoutingModule { }
