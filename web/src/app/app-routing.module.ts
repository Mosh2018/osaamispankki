import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './auth/login/login.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './helpers/auth-guard';
import {MainPageComponent} from './auth/main-page/main-page.component';
import {SignComponent} from './auth/sign/sign.component';
import {RegistrationSuccessComponent} from './auth/registration-success/registration-success.component';


const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'success-registration', component: RegistrationSuccessComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
