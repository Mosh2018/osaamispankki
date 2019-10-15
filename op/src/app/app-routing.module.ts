import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserModule} from './components/webpage/user/user.module';
import {SuccessComponent} from './components/login/success/success.component';
import {MainPageComponent} from './components/webpage/main-page/main-page.component';


const routes: Routes = [
  {path: 'main', component: MainPageComponent},
  {path: 'success', component: SuccessComponent},
  {path: 'user',
    loadChildren: () => import(`./components/webpage/user/user.module`).then( m => m.UserModule)},
  { path: 'company',
    loadChildren: () => import('./components/webpage/company/company.module').then(m => m.CompanyModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, UserModule]
})
export class AppRoutingModule { }
